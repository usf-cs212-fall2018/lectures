import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates a slightly better option than {@link SlowMultithreadedDirectoryListing}
 * for making a multithreaded version of {@link SerialDirectoryListing}. However,
 * there are still performance issues caused by creating so many little worker
 * thread objects, which have to be cleaned up by our garbage collector later.
 */
public class MultithreadedDirectoryListing {

	private static final Logger log = LogManager.getLogger();

	private final Set<Path> paths;

	/*
	 * Keeping track of "pending" or "unfinished" work will take place of our
	 * join calls. Now only the main thread has to wait, and it just has to
	 * wait for ALL the workers to be done (not just the one worker it created).
	 */
	private int pending;

	private MultithreadedDirectoryListing() {
		this.paths = new HashSet<>();
		this.pending = 0;
	}

	private void parse(Path path) {
		Thread worker = new DirectoryWorker(path);
		worker.start();
		join(); // our custom join() method
	}

	private class DirectoryWorker extends Thread {

		private final Path path;

		public DirectoryWorker(Path path) {
			this.path = path;

			// now we have to keep track of when we have new "pending" or "unfinished" work
			incrementPending();

			log.debug("Worker for {} created.", path);
		}

		@Override
		public void run() {
			Set<Path> local = new HashSet<>();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					/*
					 * Synchronized blocks inside of loops are rarely a good thing! Lets
					 * collect local, unshared data that we don't have to synchronize and
					 * do a single update later so we spend less time blocking other threads
					 */
					local.add(current);

					if (Files.isDirectory(current)) {
						Thread worker = new DirectoryWorker(current);
						worker.start();

						/*
						 * Note that we no longer need to join() on the worker. That means
						 * other threads can keep on going without having to wait for each
						 * other. Only main needs to wait for all the work to be done.
						 */
					}
				}

				/*
				 * Now, we will block and make our big update. It is less likely threads
				 * will block because they will finish their work at different times, and
				 * we spend less time locking/unlocking and hence less time causing blocking
				 */
				synchronized (paths) {
					paths.addAll(local);
				}
			}
			catch (IOException ex) {
				log.debug(ex.getMessage(), ex);
			}

			log.debug("Worker for {} finished.", path);
			// almost done! now we can indicate we have 1 less "pending" work
			decrementPending();
		}
	}

	/*
	 * Since multiple threads need to modify the pending variable, of course
	 * it should be synchronized!
	 */

	private synchronized void incrementPending() {
		pending++;
	}

	private synchronized void decrementPending() {
		assert pending > 0;
		pending--;

		/*
		 * How often should we call notifyAll()? We don't want to overdo it.
		 * (See log if we take out the if pending == 0.)
		 */

		if (pending == 0) {
			this.notifyAll();
		}
	}

	/*
	 * Our custom join is going to wait until all unfinished work is complete by
	 * waiting until the pending variable hits 0.
	 */

	private synchronized void join() {
		try {
			log.debug("Waiting for work...");

			while (pending > 0) {
				// if we put a wait() here... where does the notifyAll() go?
				this.wait();
				log.debug("Woke up with pending at {}.", pending);
			}

			log.debug("Work finished.");
		}
		catch (InterruptedException ex) {
			log.debug(ex.getMessage(), ex);
		}
	}

	public static Set<Path> list(Path path) {
		MultithreadedDirectoryListing workers = new MultithreadedDirectoryListing();
		workers.parse(path);
		return workers.paths;
	}

	public static void main(String[] args) {
		Set<Path> paths = list(Paths.get("."));
		paths.stream().forEach(System.out::println);
	}
}

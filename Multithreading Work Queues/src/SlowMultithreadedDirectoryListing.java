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
 * This class demonstrates a simple but inefficient approach to multithreading
 * the {@link SerialDirectoryListing} class. It still has a single public
 * static method, but there are several private instance methods in here to
 * help with the multithreading aspects.
 */
public class SlowMultithreadedDirectoryListing {

	private static final Logger log = LogManager.getLogger();

	private final Set<Path> paths;

	/*
	 * Note this is public. We will only call this constructor in our list()
	 * method below.
	 */

	private SlowMultithreadedDirectoryListing() {
		this.paths = new HashSet<>();
	}

	private void parse(Path path) {
		Thread worker = new DirectoryWorker(path);
		worker.start();

		/*
		 * Now what? How do we wait for work to be done? Well, the main thread
		 * could call join() on the worker here. But, this becomes non-ideal when
		 * we get into the run() method of our workers...
		 */

		try {
			worker.join();
		}
		catch (InterruptedException ex) {
			log.debug(ex.getMessage(), ex);
		}
	}

	private class DirectoryWorker extends Thread {

		private final Path path;

		public DirectoryWorker(Path path) {
			this.path = path;
			log.debug("Worker for {} created.", path);
		}

		@Override
		public void run() {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					/*
					 * We have to protect all access to our shared data now! It is common
					 * to use the object you are trying to protect as the lock itself.
					 *
					 * This is really slow though, it causes tons of locking/unlocking
					 * which itself causes a bunch of blocking.
					 */
					synchronized (paths) {
						paths.add(current);
					}

					if (Files.isDirectory(current)) {
						/*
						 * Instead of a recursive call, we will create a new worker to
						 * deal with the subdirectory. Our workers create new workers!
						 */
						Thread worker = new DirectoryWorker(current);
						worker.start();

						/*
						 * Uh oh... how to we wait for our new worker to be done? If we
						 * call join here... well, basically only 1 thread is really going
						 * to get a chance to run at a time. Check out the logs to be sure!
						 */
						worker.join();
					}
				}
			}
			catch (IOException | InterruptedException ex) {
				log.debug(ex.getMessage(), ex);
			}

			log.debug("Worker for {} finished.", path);
		}
	}

	public static Set<Path> list(Path path) {
		SlowMultithreadedDirectoryListing workers = new SlowMultithreadedDirectoryListing();
		workers.parse(path);
		return workers.paths;
	}

	public static void main(String[] args) {
		Set<Path> paths = list(Paths.get("."));
		paths.stream().forEach(System.out::println);
	}
}

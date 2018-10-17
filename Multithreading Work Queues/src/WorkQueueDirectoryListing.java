import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkQueueDirectoryListing {

	private static final Logger log = LogManager.getLogger();

	private final Set<Path> paths;
	private final WorkQueue queue;
	private int pending;

	private WorkQueueDirectoryListing() {
		this.paths = new HashSet<>();
		this.pending = 0;
		this.queue = new WorkQueue();
	}

	private void parse(Path path) {
		queue.execute(new DirectoryTask(path));
		join();
	}

	private class DirectoryTask implements Runnable {

		private final Path path;

		public DirectoryTask(Path path) {
			this.path = path;
			incrementPending();
			log.debug("Worker for {} created.", path);
		}

		@Override
		public void run() {
			Set<Path> local = new HashSet<>();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					local.add(current);

					if (Files.isDirectory(current)) {
						queue.execute(new DirectoryTask(current));
					}
				}

				synchronized (paths) {
					paths.addAll(local);
				}
			}
			catch (IOException ex) {
				log.debug(ex.getMessage(), ex);
			}

			log.debug("Worker for {} finished.", path);
			decrementPending();
		}
	}

	private synchronized void incrementPending() {
		pending++;
	}

	private synchronized void decrementPending() {
		assert pending > 0;
		pending--;

		if (pending == 0) {
			this.notifyAll();
		}
	}

	private synchronized void join() {
		try {
			log.debug("Waiting for work...");

			while (pending > 0) {
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
		WorkQueueDirectoryListing workers = new WorkQueueDirectoryListing();
		workers.parse(path);
		workers.queue.shutdown();
		return workers.paths;
	}

	public static void main(String[] args) {
		Set<Path> paths = list(Paths.get("."));
		paths.stream().forEach(System.out::println);
	}
}

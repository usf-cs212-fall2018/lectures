import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlowMultithreadedDirectoryListing {

	private static final Logger log = LogManager.getLogger();

	private final Set<Path> paths;

	private SlowMultithreadedDirectoryListing() {
		this.paths = new HashSet<>();
	}

	private void parse(Path path) {
		Thread worker = new DirectoryWorker(path);
		worker.start();

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
					synchronized (paths) {
						paths.add(current);
					}

					if (Files.isDirectory(current)) {
						Thread worker = new DirectoryWorker(current);
						worker.start();
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

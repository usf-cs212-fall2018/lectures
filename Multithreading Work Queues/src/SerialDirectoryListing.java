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
 * Generates a directory listing using single-threading.
 */
public class SerialDirectoryListing {

	private static final Logger log = LogManager.getLogger();

	/**
	 * Returns a directory listing for the given path.
	 *
	 * @param path directory to create listing
	 * @return paths found within directory and its subdirectories
	 */
	public static Set<Path> list(Path path) {
		HashSet<Path> paths = new HashSet<>();
		list(path, paths);
		return paths;
	}

	private static void list(Path path, Set<Path> paths) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path current : stream) {
				paths.add(current);

				if (Files.isDirectory(current)) {
					// this recursive call is what we'll convert to worker tasks later
					list(current, paths);
				}
			}
		}
		catch (IOException ex) {
			log.debug(ex.getMessage(), ex);
		}
	}

	/**
	 * Tests the directory listing for the current directory.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		list(Paths.get(".").normalize()).stream().forEach(System.out::println);
	}
}

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerialDirectoryListing {

	private static final Logger log = LogManager.getLogger();

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
					list(current, paths);
				}
			}
		}
		catch (IOException ex) {
			log.debug(ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) {
		list(Paths.get(".").normalize()).stream().forEach(System.out::println);
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class TextFileStreamer {

	public static void streamTextFile1(
			Path path
	) throws IOException {

		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		) {
			// TODO
		}
	}

	public static List<String> listWords1(Path path, Function<String, String> clean)
			throws IOException {
		// TODO
		return null;
	}

	public static void streamTextFile2(
			Path path
	) throws IOException {

		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		) {
			// TODO
		}
	}

	public static List<String> listWords2(Path path, Function<String, String> clean)
			throws IOException {
		// TODO
		return null;
	}

	public static void main(String[] args) throws IOException {
		Path sally = Paths.get("sally.txt");
		Function<String, String> clean = s -> s.toLowerCase().replaceAll("[^A-z\\s]+", " ");

		// TODO
	}

}

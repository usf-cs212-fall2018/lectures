import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class ParallelStreamDemo {

	public static long countWordsConcat(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsBuffer(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsNormal(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsStream(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {

			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsParallelStream(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {

			long count = 0;

			// TODO

			return count;
		}
	}

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("1400-0.txt");
		String word = "great";

		Function<String, String[]> tokenize = line -> line.toLowerCase().split("[^\\p{Alpha}]+");
		System.out.println(Arrays.toString(tokenize.apply("The Project Gutenberg EBook of Great Expectations, by Charles Dickens")));

		long count = 0;
		long start = System.nanoTime();

		// TODO
//		count = countWordsConcat(path, word, tokenize);
//		count = countWordsBuffer(path, word, tokenize);
//		count = countWordsNormal(path, word, tokenize);
//		count = countWordsStream(path, word, tokenize);
//		count = countWordsParallelStream(path, word, tokenize);

		long elapsed = System.nanoTime() - start;

		String format = "Found %d occurrences of \"%s\" in %f seconds.";
		System.out.printf(format, count, word, elapsed / 1000000000.0);
	}

}

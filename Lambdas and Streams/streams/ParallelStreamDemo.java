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
			String text = "";
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				text += line + "\n";
			}

			for (String token : tokenize.apply(text)) {
				if (token.equals(word)) {
					count++;
				}
			}

			return count;
		}
	}

	public static long countWordsBuffer(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			StringBuffer text = new StringBuffer();
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}

			for (String token : tokenize.apply(text.toString())) {
				if (token.equals(word)) {
					count++;
				}
			}

			return count;
		}
	}

	public static long countWordsNormal(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				for (String token : tokenize.apply(line)) {
					if (token.equals(word)) {
						count++;
					}
				}
			}

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

			return stream
				.flatMap(line -> Arrays.stream(tokenize.apply(line)))
				.filter(token -> token.equals(word))
				.count();
		}
	}

	public static long countWordsParallelStream(
			Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {

			return stream
				.parallel()
				.flatMap(line -> Arrays.stream(tokenize.apply(line)))
				.filter(token -> token.equals(word))
				.count();
		}
	}

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("1400-0.txt");
		String word = "great";

		Function<String, String[]> tokenize = line -> line.toLowerCase().split("[^\\p{Alpha}]+");
		System.out.println(Arrays.toString(tokenize.apply("The Project Gutenberg EBook of Great Expectations, by Charles Dickens")));

		long start = System.nanoTime();

//		long count = countWordsConcat(path, word, tokenize);
//		long count = countWordsBuffer(path, word, tokenize);
//		long count = countWordsNormal(path, word, tokenize);
//		long count = countWordsStream(path, word, tokenize);
		long count = countWordsParallelStream(path, word, tokenize);

		long elapsed = System.nanoTime() - start;

		String format = "Found %d occurrences of \"%s\" in %f seconds.";
		System.out.printf(format, count, word, elapsed / 1000000000.0);
	}

}

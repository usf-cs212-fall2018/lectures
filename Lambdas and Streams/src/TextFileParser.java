import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class created to demonstrate lambda functions (not yet streams).
 */
public class TextFileParser {

	/**
	 * Splits each line at the specified path by whitespace, applies the provided
	 * clean method to each token, and returns the result as a list.
	 *
	 * @param path the path to read
	 * @param clean the function to apply to each token
	 * @return list of split and cleaned words
	 * @throws IOException
	 */
	public static List<String> listWords(Path path, Function<String, String> clean)
			throws IOException {
		List<String> words = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");

				for (String token : tokens) {
					String cleaned = clean.apply(token);
					words.add(cleaned);
				}
			}
		}

		return words;
	}

	/**
	 * Splits each line at the specified path by whitespace, and performs an action
	 * on each of the resulting tokens.
	 *
	 * @param path the path to read
	 * @param action the action to perform on each token
	 * @throws IOException
	 */
	public static void parseWords(Path path, Consumer<String> action)
			throws IOException {
		try (
				BufferedReader reader = Files
				.newBufferedReader(path, StandardCharsets.UTF_8)
				) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");

				for (String token : tokens) {
					action.accept(token);
				}
			}
		}
	}

	/**
	 * Splits each line at the specified path by whitespace, applies the provided
	 * clean method to each token, and performs an action on each of the resulting
	 * tokens.
	 *
	 * @param path   the path to read
	 * @param clean  the function to apply to each token
	 * @param action the action to perform on each token
	 * @throws IOException
	 */
	public static void parseWords(Path path, Function<String, String> clean,
			Consumer<String> action) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");

				for (String token : tokens) {
					String cleaned = clean.apply(token);
					action.accept(cleaned);
				}
			}
		}
	}

	/**
	 * A reimplementation of {@link #listWords(Path, Function)} reusing the
	 * {@link #parseWords(Path, Function, Consumer)} method.
	 *
	 * @param path   the path to read
	 * @param clean  the function to apply to each token
	 * @return list of split and cleaned words
	 * @throws IOException
	 */
	public static List<String> listWords2(Path path, Function<String, String> clean)
			throws IOException {
		List<String> words = new ArrayList<>();
		parseWords(path, clean, words::add);
		return words;
	}

	/**
	 * Removes punctuation from the provided text.
	 *
	 * @param text to remove punctuation
	 * @return text without punctuation
	 */
	public static String removePunctuation(String text) {
		return text.replaceAll("(?U)\\p{Punct}+", "");
	}

	/**
	 * Demonstrates different lambda expressions and method notations for the
	 * functions defined in this class.
	 *
	 * @param args unused
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("sally.txt");

		// Use lambda expressions to convert everything to lowercase
		List<String> list = listWords(path, s -> s.toLowerCase());
		System.out.println(list);

		// Use method notation instead
		System.out.println(listWords(path, String::toUpperCase));
		System.out.println(listWords(path, TextFileParser::removePunctuation));

		// Use parse words and add to a set
		TreeSet<String> set = new TreeSet<>();
		parseWords(path, set::add);
		System.out.println(set);

		// Use parse words to output to the console instead
		Function<String, String> clean = s -> removePunctuation(s).toLowerCase() + " ";
		parseWords(path, clean, System.out::print);
		System.out.println();

		// Use the re-implementation of listWords
		System.out.println(listWords2(path, String::toLowerCase));
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextFileStreamer {

	/*
	 * An initial approach trying to re-implement our methods in TextFileParser
	 * using streams instead. However, when we use this to add to a collection
	 * defined outside our stream, we are creating code with dangerous side-effects!
	 */

	public static void streamTextFile(
			Path path,
			Function<String, String> clean,
			Function<String, String[]> tokenize,
			Consumer<String> consumer
	) throws IOException {

		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> lines = reader.lines();
		) {
			lines
				.map(clean)
				.flatMap(line -> Stream.of(tokenize.apply(line)))
				.forEach(consumer);
		}
	}

	public static List<String> listWords1(Path path, Function<String, String> clean)
			throws IOException {
		List<String> words = new ArrayList<String>();
		streamTextFile(path, clean, s -> s.split("\\s+"), words::add);
		return words;
	}

	/*
	 * A better approach that has no dangerous side-effects.
	 */

	public static <C extends Collection<String>> C streamTextFile(
			Path path,
			Function<String, String> clean,
			Function<String, String[]> tokenize,
			Supplier<C> collector
	) throws IOException {

		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> lines = reader.lines();
		) {
			return lines
				.map(clean)
				.flatMap(line -> Stream.of(tokenize.apply(line)))
				.collect(Collectors.toCollection(collector));
		}
	}

	public static List<String> listWords2(Path path, Function<String, String> clean)
			throws IOException {
		return streamTextFile(path, clean, s -> s.split("\\s+"), ArrayList::new);
	}

	public static void main(String[] args) throws IOException {
		Path sally = Paths.get("sally.txt");

		Function<String, String> clean = s -> s.toLowerCase().replaceAll("[^A-z\\s]+", " ");
		System.out.println(listWords1(sally, clean));
		System.out.println(listWords2(sally, clean));
		System.out.println(streamTextFile(sally, clean, s -> s.split("\\s+"), TreeSet::new));
	}

	/*
	 * Have we gone too far? This does demonstrate how we could use streams and
	 * lambdas to continue and generalize our approach to TextFileParser, but
	 * honestly the core operations in streamTextFile are compact and simple
	 * enough that you really could just recreate and customize it as necessary.
	 */
}

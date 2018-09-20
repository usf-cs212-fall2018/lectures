import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamDemo {

	public static void print(String element) {
		System.out.print(element);
		System.out.print(" ");
	}

	public static void main(String[] args) throws IOException {

		/*
		 * https://en.wikipedia.org/wiki/List_of_computing_and_IT_abbreviations
		 * API - Application Program Interface
		 * BLOB - Binary Large OBject
		 * CPU - Central Processing Unit
		 * DBMS - DataBase Management System
		 * EOF - End Of File
		 * FIFO - First In First Out
		 * GUI - Fraphical User Interface
		 * HDD - HardDisk Drive
		 * IDE - Integrated Development Environment
		 * JSON - JavaScript Object Notation
		 * OOP - Object Oriented Programming
		 * URI - Uniform Resource Identifier
		 * WYSIWYG - What You See Is What You Get
		 */

		ArrayList<String> acronyms = new ArrayList<>();
		Collections.addAll(acronyms, "IDE", "CPU", "URI", "HDD", "GUI", "API", "OOP", "EOF");
		Collections.addAll(acronyms, "DBMS", "BLOB", "FIFO", "JSON");
		Collections.addAll(acronyms, "WYSIWYG");

		int i = 0;

		// Use a method reference to print on one line
		// (This is not a stream.)
		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// Get a steam with the acronyms as its data source
		Stream<String> stream = acronyms.stream();

		// Demonstrate that the stream is not modifying its source
		System.out.print(++i + ": ");
		stream.sorted().forEach(StreamDemo::print);
		System.out.println();

		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// Demonstrate that collection operations do modify its source
		// Demonstrate that the collection can be reused
		acronyms.sort(null);

		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// Demonstrate that the stream cannot be reused
		try {
			System.out.print(++i + ": ");
			stream.sorted().forEach(StreamDemo::print);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Demonstrate lazy versus eager
		// Find the first three-letter acronym that has no vowels

		Pattern regex = Pattern.compile("[^AIEOU]+");

		// Using collections
		System.out.print(++i + ": ");
		for (String acronym : acronyms) {
			print(acronym);
			if (acronym.length() == 3) {
				print(acronym);
				if (regex.matcher(acronym).matches()) {
					print(acronym);
					break;
				}
			}
		}
		System.out.println();

		// Using streams
		System.out.print(++i + ": ");
		acronyms.stream()
			.filter(s -> {
				print(s);
				return s.length() == 3;
			})
			.filter(s -> {
				print(s);
				return regex.matcher(s).matches();
			})
			.findFirst()
			.ifPresent(StreamDemo::print);
		System.out.println();

		/*
		 * In both cases, the same output is produced. The second filter is not
		 * executed unless necessary, and findFirst is going to terminate the
		 * stream in the same way our break terminated the for loop.
		 */

		// Demonstrate other data sources
		System.out.print(++i + ": ");
		Path sally = Paths.get("sally.txt");
		try (Stream<String> lines = Files.newBufferedReader(sally).lines()) {
			lines
				.flatMap(line -> Stream.of(line.split("\\s+")))
				.map(TextFileParser::removePunctuation)
				.map(String::toLowerCase)
				.filter(word -> word.startsWith("s"))
				.forEach(StreamDemo::print);
		}


	}

}

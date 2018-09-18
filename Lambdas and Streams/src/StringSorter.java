import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class created to demonstrate lambda functions (not yet streams).
 */
public class StringSorter {

	public static class StringIncreasingLength implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return Integer.compare(o1.length(), o2.length());
		}
	};

	public static void printList(String label, List<String> words) {
		System.out.printf("%9s: %s%n", label, words);
	}

	public static void main(String[] args) {
		// initialize the test case
		List<String> words = new ArrayList<>();
		Collections.addAll(words, new String[] {
				"ant", "AARDVARK", "Bee", "capybara", "deer", "elk", "elephant", "FOX",
				"gecko", "hippopatamus" });
		printList("Original", words);

		// demonstrate shuffle operation
		Collections.shuffle(words);
		printList("Shuffled", words);

		// sort via natural ordering and output
		Collections.shuffle(words);
		Collections.sort(words);
		printList("Natural", words);

		// sort using case-insensitive ordering
		Collections.shuffle(words);
		words.sort(String.CASE_INSENSITIVE_ORDER);
		printList("No Case", words);

		// create a custom comparator static nested class sorting by length
		Collections.shuffle(words);
		words.sort(new StringIncreasingLength());
		printList("Length", words);

		// create a custom comparator anonymous inner class sorting by length
		Comparator<String> increasingLength = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		};
		Collections.shuffle(words);
		words.sort(increasingLength);
		printList("Length", words);

		// embed the same anonymous inner class definition within the sort method
		Collections.shuffle(words);
		words.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		});
		printList("Length", words);

		// convert the method to lambda expressions
		Collections.shuffle(words);
		words.sort((o1, o2) -> Integer.compare(o1.length(), o2.length()));
		printList("Length", words);

		// alternate form of conversion (using other lambda notation)
		Collections.shuffle(words);
		words.sort(Comparator.comparingInt(String::length));
		printList("Length", words);

		// reversed form of conversion
		Collections.shuffle(words);
		words.sort(Comparator.comparingInt(String::length).reversed());
		printList("Reversed", words);

		// sort by length and then case-insensitive sort by text
		Collections.shuffle(words);
		words.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int same = Integer.compare(o1.length(), o2.length());
				return same != 0 ? same : String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
			}
		});
		printList("Combined", words);

		// converted to lambda function
		Collections.shuffle(words);
		words.sort((o1, o2) -> {
			int same = Integer.compare(o1.length(), o2.length());
			return same != 0 ? same : String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
		});
		printList("Combined", words);

		// alternate form of conversion
		Collections.shuffle(words);
		words.sort(Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));
		printList("Combined", words);
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringSorter {

	public static void printList(String label, List<String> words) {
		System.out.printf("%9s: %s%n", label, words);
	}

	public static void main(String[] args) {
		List<String> words = new ArrayList<>();
		Collections.addAll(words, new String[] {
				"ant", "AARDVARK", "Bee", "capybara", "deer", "elk", "elephant", "FOX",
				"gecko", "hippopatamus" });

		printList("Original", words);

		Collections.shuffle(words);
		printList("Shuffled", words);

		Collections.shuffle(words);
		Collections.sort(words);
		printList("Natural", words);

		// TODO
	}
}

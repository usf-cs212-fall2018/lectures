import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * This class demonstrates the {@link Comparable} and {@link Comparator}
 * interfaces and how to use these elements to change how a list of objects is
 * sorted.
 */
public class SortDemo {

	// What should be the type of parameter? List? Set? Collection?
	/**
	 * Able to print to console any {@link Iterable} of {@link #Widget} objects.
	 * Used to debug.
	 *
	 * @param widgets the iterable collection to be printed to the console
	 */
	public static void printCollection(Iterable<Widget> widgets) {
		for (Widget widget : widgets) {
			System.out.printf("id: %2d name: %s\n", widget.id, widget.name);
		}
	}

	/**
	 * Demonstrates how to sort the {@link ArrayList} of {@link #Widget} objects
	 * using the {@link Comparable} and {@link Comparator} interfaces and the
	 * {@link Collections#sort(java.util.List)} methods.
	 *
	 * @param args unused
	 *
	 * @see {@link Comparable}
	 * @see {@link Comparator}
	 * @see {@link Collections#sort(java.util.List)}
	 * @see {@link Collections#sort(java.util.List, Comparator)}
	 */
	public static void main(String[] args) {
		ArrayList<Widget> list = new ArrayList<Widget>();
		list.add(new Widget(10, "ant"));
		list.add(new Widget(7, "hippopotamus"));
		list.add(new Widget(14, "dragonfly"));
		list.add(new Widget(3, "camel"));

		// unsorted
		System.out.println("ArrayList, Unsorted:");
		printCollection(list);

		// sorted by name length
		System.out.println("\nArrayList, Sorted by ID:");
		Collections.sort(list);
		printCollection(list);

		// sorted by name length
		System.out.println("\nArrayList, Sorted by Name Length:");
		Collections.sort(list, new WidgetComparator());
		printCollection(list);

		// sorted by name
		System.out.println("\nArrayList, Sorted by Name");
		Collections.sort(list, new Comparator<Widget>() {
			@Override
			public int compare(Widget w1, Widget w2) {
				return String.CASE_INSENSITIVE_ORDER.compare(w1.name, w2.name);
			}
		});
		printCollection(list);

		// sorted by id
		System.out.println("\nTreeSet, Sorted by ID:");
		TreeSet<Widget> set1 = new TreeSet<Widget>();
		set1.addAll(list);
		printCollection(set1);

		// sorted by name length
		System.out.println("\nTreeSet, Sorted by Name Length:");
		TreeSet<Widget> set2 = new TreeSet<Widget>(new WidgetComparator());
		set2.addAll(list);
		printCollection(set2);
	}
}

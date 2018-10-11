import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

public class IndexedSet<E> {

	private Set<E> set;

	public IndexedSet() {
		this(false);
	}

	public IndexedSet(boolean sorted) {
		// TODO
	}

	public boolean add(E element) {
		return set.add(element);
	}

	public boolean addAll(Collection<E> elements) {
		return set.addAll(elements);
	}

	public int size() {
		return set.size();
	}

	public boolean contains(E element) {
		return set.contains(element);
	}

	public E get(int index) {
		// TODO
		return null;
	}

	@Override
	public String toString() {
		return set.toString();
	}

	public Set<E> unsortedCopy() {
		return null; // TODO
	}

	public SortedSet<E> sortedCopy() {
		return null; // TODO
	}

	public static void main(String[] args) {
		ArrayList<String> elements = new ArrayList<>();
		Collections.addAll(elements, "ant", "fox", "fly", "bee");

		IndexedSet<String> sorted = new IndexedSet<>(true);
		IndexedSet<String> unsorted = new IndexedSet<>();

		sorted.addAll(elements);
		unsorted.addAll(elements);

		System.out.println(sorted);
		System.out.println(sorted.unsortedCopy());

		System.out.println(unsorted);
		System.out.println(unsorted.sortedCopy());

		System.out.println(sorted.get(0));
		System.out.println(unsorted.get(0));

		System.out.println(sorted.get(3));
		System.out.println(unsorted.get(3));

		System.out.println(sorted.get(10));
		System.out.println(unsorted.get(10));

	}
}

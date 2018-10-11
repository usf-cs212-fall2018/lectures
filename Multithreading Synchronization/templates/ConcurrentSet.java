import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

public class ConcurrentSet<E> extends IndexedSet<E> {

	// TODO

	public ConcurrentSet() {
		this(false);
	}

	public ConcurrentSet(boolean sorted) {
		super(sorted);
	}

	@Override
	public boolean add(E element) {
		return super.add(element);
	}

	@Override
	public boolean addAll(Collection<E> elements) {
		return super.addAll(elements);
	}

	@Override
	public int size() {
		return super.size();
	}

	@Override
	public boolean contains(E element) {
		return super.contains(element);
	}

	@Override
	public E get(int index) {
		return super.get(index);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public Set<E> unsortedCopy() {
		return super.unsortedCopy();
	}

	@Override
	public SortedSet<E> sortedCopy() {
		return super.sortedCopy();
	}
}

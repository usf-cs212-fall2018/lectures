/**
 * A simple class that knows how to compare against other objects of this class
 * by implementing the {@link Comparable} interface.
 */
public class Widget implements Comparable<Widget> {
	public int id;
	public String name;

	public Widget(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * By default, {@link #Widget} objects will be sorted by the widget id.
	 */
	@Override
	public int compareTo(Widget other) {
		return Integer.compare(this.id, other.id);
	}
}

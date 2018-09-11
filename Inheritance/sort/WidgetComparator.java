import java.util.Comparator;

/**
 * This class implements {@link Comparator} to change how {@link #Widget}
 * objects are sorted.
 */
public class WidgetComparator implements Comparator<Widget> {
	/**
	 * Will sort {@link Widget} objects by the length of the name instead of the
	 * id.
	 */
	@Override
	public int compare(Widget w1, Widget w2) {
		int length1 = w1.name.length();
		int length2 = w2.name.length();

		// Use Integer.compare(length2, length1) to swap order.
		return Integer.compare(length1, length2);
	}
}

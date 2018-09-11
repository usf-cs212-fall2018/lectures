import java.util.ArrayList;

public class SortDemo {

	public static void printCollection(Object widgets) {
		// TODO
		//System.out.printf("id: %2d name: %s\n", widget.id, widget.name);
	}

	public static void main(String[] args) {
		ArrayList<Widget> list = new ArrayList<Widget>();
		list.add(new Widget(10, "ant"));
		list.add(new Widget(7, "hippopotamus"));
		list.add(new Widget(14, "dragonfly"));
		list.add(new Widget(3, "camel"));

		System.out.println("ArrayList, Unsorted:");
		printCollection(list);

		// TODO
	}
}

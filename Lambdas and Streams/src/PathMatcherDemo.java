import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.function.Predicate;

public class PathMatcherDemo {

	/**
	 * Demonstrates the examples in the lamda expression lecture slides.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Path path1 = Paths.get("hello.txt");
		Path path2 = Paths.get("txt.hello");

		// anonymous inner class
		PathMatcher m1 = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(path1 + ": " + m1.matches(path1));
		System.out.println(path2 + ": " + m1.matches(path2));
		System.out.println();

		// verbose lambda expression
		PathMatcher m2 = (Path p) -> {
			return p.toString().endsWith(".txt");
		};

		System.out.println(path1 + ": " + m2.matches(path1));
		System.out.println(path2 + ": " + m2.matches(path2));
		System.out.println();

		// compact lambda expression
		PathMatcher m3 = p -> p.toString().endsWith(".txt");

		System.out.println(path1 + ": " + m3.matches(path1));
		System.out.println(path2 + ": " + m3.matches(path2));
		System.out.println();

		// changing the identifier type
		Predicate<Path> m4 = p -> p.toString().endsWith(".txt");

		System.out.println(path1 + ": " + m4.test(path1));
		System.out.println(path2 + ": " + m4.test(path2));
		System.out.println();
	}

}

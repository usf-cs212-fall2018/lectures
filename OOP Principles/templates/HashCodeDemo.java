import java.util.Arrays;

public class HashCodeDemo {

	public static void printHash(String label, Object object) {
		Object[] args = { label, System.identityHashCode(object), object };
		System.out.format("%-9s : x%08X : %s %n", args);
	}

	public static void printHash(String label, Object[] object) {
		Object[] args = { label, System.identityHashCode(object), Arrays.toString(object) };
		System.out.format("%-9s : x%08X : %s %n", args);
	}

	// TODO

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		// TODO

	}

}

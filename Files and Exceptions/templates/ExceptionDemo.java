import java.util.Scanner;

public class ExceptionDemo {

	private static int calcPercentage(int total, int possible) {
		return 100 * total / possible;
	}

	private static void printResult(int total, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", total, possible, percentage);
	}

	public static void uncaughtDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		// TODO

		//		System.out.print("Enter total points earned: ");
		//		System.out.print("Enter total points possible: ");
		//		System.out.println("[done]");
	}

	@SuppressWarnings("resource")
	public static void validationDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		// TODO

		//		System.out.print("Enter total points earned: ");
		//		System.err.println("Please enter integer values.");
		//		System.out.print("Enter total points possible: ");
		//		System.err.println("Please enter integer values.");
		//		System.err.println("Please enter non-negative values.");
		//		System.out.println("[done]");
	}

	public static void catchAllDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = null;

		try {

			// TODO

			//			System.out.print("Enter total points earned: ");
			//			System.out.print("Enter total points possible: ");
		}
		catch (Exception e) {

			// TODO

		}
		finally {

			// TODO

			//			System.out.println("[done]");
		}
	}

	public static void resourcefulDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		// TODO

		//		System.out.print("Enter total points earned: ");
		//		System.out.print("Enter total points possible: ");
		//		throw new IllegalArgumentException("Values must be non-negative.");
		//		System.err.println("Please enter integer values.");
		//		System.err.println("Please enter non-negative values.");
		//		System.err.println(e.toString());
		//		System.out.println("[done]");
	}

	public static void main(String[] args) {
		//		 uncaughtDemo();
		//		 validationDemo();
		//		 catchAllDemo();
		//		resourcefulDemo();
	}
}

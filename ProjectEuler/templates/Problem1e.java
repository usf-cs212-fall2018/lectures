import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem1e {

	public static List<Integer> getMultiples(int value, int max) {
		// TODO
		Set<Integer> multiples = new TreeSet<>();

		for (int i = value; i < max; i += value) {
			multiples.add(i);
		}

		return multiples;
	}

	public static int sumMultiples(Collection<Integer> values, int max) {
		// TODO
		Set<Integer> multiples = new TreeSet<>();

		for (int value : values) {
			multiples.addAll(getMultiples(value, max));
		}

		int sum = 0;

		for (int value : multiples) {
			sum += value;
		}

		return sum;
	}

	public static void main(String[] args) {
		int max = 0;
		List<Integer> values = new ArrayList<>();

		try {
			max = Integer.parseInt(args[0]);

			if (max < 0) {
				throw new NumberFormatException("Integer value must be non-negative.");
			}

			for (int i = 1; i < args.length; i++) {
				int value = Integer.parseInt(args[i]);

				if (value < 0) {
					throw new NumberFormatException(
							"Integer value must be non-negative.");
				}

				values.add(value);
			}

			int result = sumMultiples(values, max);
			System.out.printf("The sum of multiples of %s less than %d is %d.",
					values.toString(), max, result);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("At least two values must be provided.");
		}
		catch (NumberFormatException e) {
			System.err.println("All values must be non-negative integers.");
		}
	}
}

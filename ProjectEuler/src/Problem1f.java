import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Project Euler Problem 1 is stated as follows:
 *
 * <blockquote> If we list all the natural numbers below 10 that are multiples
 * of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the
 * sum of all the multiples of 3 or 5 below 1000. </blockquote>
 *
 * This example provides a simple benchmark class to compare the average run
 * times of different approaches to this problem.
 */
public class Problem1f {

	public static void main(String[] args) {
		// test case
		int max = 100000;
		List<Integer> values = Stream
				.of(3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
						71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
						149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199)
				.collect(Collectors.toList());

		// sanity check values
		int sum1 = Problem1d.sumMultiples(values, max);
		int sum2 = Problem1e.sumMultiples(values, max);

		System.out.println("Sanity check: " + (sum1 == sum2));

		// benchmark parameters
		int warmup = 5;
		int rounds = 30;
		double elapsed = 0;

		// 1d warmup
		for (int i = 0; i < warmup; i++) {
			Problem1d.sumMultiples(values, max);
		}

		// 1d benchmark
		elapsed = System.currentTimeMillis();

		for (int i = 0; i < rounds; i++) {
			Problem1d.sumMultiples(values, max);
		}

		elapsed = (System.currentTimeMillis() - elapsed) / rounds;
		System.out.printf("Problem 1d seconds (avg): %.4f%n", (elapsed / 1000));

		// 1e warmup
		for (int i = 0; i < warmup; i++) {
			Problem1e.sumMultiples(values, max);
		}

		// 1e benchmark
		elapsed = System.currentTimeMillis();

		for (int i = 0; i < rounds; i++) {
			Problem1e.sumMultiples(values, max);
		}

		elapsed = (System.currentTimeMillis() - elapsed) / rounds;
		System.out.printf("Problem 1e seconds (avg): %.4f%n", (elapsed / 1000));
	}
}

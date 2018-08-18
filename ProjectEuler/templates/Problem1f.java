import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		// TODO

//		for (int i = 0; i < warmup; i++) {
//			Problem1e.sumMultiples(values, max);
//		}
//
//		elapsed = System.currentTimeMillis();
//
//		for (int i = 0; i < rounds; i++) {
//			Problem1e.sumMultiples(values, max);
//		}
//
//		elapsed = (System.currentTimeMillis() - elapsed) / rounds;
//		System.out.printf("Problem 1e seconds (avg): %.4f%n", (elapsed / 1000));
	}
}

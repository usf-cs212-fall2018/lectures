import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FibonacciGenerator {

	/**
	 * Used for generating the Fibonacci sequence recursively using memorization.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci number</a>
	 */
	public static class FibonacciPair {
		public final long index;
		public final BigInteger previous;
		public final BigInteger current;

		/**
		 * Initializes a Fibonacci pair for a specific sequence index. For example,
		 * for the Fibonacci sequence 0, 1, 1, 2, 3, ... the Fibonacci number at
		 * index 0 is 0 and the Fibonacci number at index 1 and index 2 is 1.
		 *
		 * @param index current sequence index (e.g. 0, 1, 2, 3, ...)
		 * @param previous value of previous Fibonacci number (i.e. at index - 1)
		 * @param current value of current Fibonacci number (i.e. at index)
		 *
		 * @throws IllegalArgumentException if a non-negative index is provided
		 */
		public FibonacciPair(long index, BigInteger previous, BigInteger current)
				throws IllegalArgumentException {
			if (index < 0) {
				throw new IllegalArgumentException("The index must be a nonnegative number.");
			}

			this.index = index;
			this.previous = previous;
			this.current = current;
		}

		/**
		 * See {@link #FibonacciGenerator(long, BigInteger, BigInteger)}.
		 */
		public FibonacciPair(long index, long previous, long current) {
			this(index, BigInteger.valueOf(previous), BigInteger.valueOf(current));
		}

		/**
		 * Generates the next Fibonacci number in the sequence.
		 *
		 * @return the next Fibonacci number in the sequence
		 */
		public FibonacciPair next() {
			// TODO: Intentionally make a mistake here.
//			return new FibonacciPair(index + 1, previous, previous.add(current));
			return new FibonacciPair(index + 1, current, previous.add(current));
		}

		@Override
		public String toString() {
			return "f(" + index + ") = " + current.toString();
		}

		/**
		 * The base case for the 0th Fibonacci number (i.e. F_0 = 0).
		 */
		public static FibonacciPair F0 = new FibonacciPair(0, 0, 0);

		/**
		 * The base case for the 1st Fibonacci number (i.e. F_1 = 1).
		 */
		// TODO: Intentionally make a mistake here.
//		public static FibonacciPair F1 = new FibonacciPair(1, 1, 1);
		public static FibonacciPair F1 = new FibonacciPair(1, 0, 1);

		/**
		 * Creates an infinite sequential stream of {@link FibonacciPair} objects
		 * starting from the 1st number (index = 1) in the sequence.
		 *
		 * @return an infinite sequential stream of {@link FibonacciPair} objects
		 */
		public static Stream<FibonacciPair> stream() {
			// TODO: Intentionally make a mistake here.
			// return Stream.iterate(F1, fn -> fn.next());
			return Stream.concat(Stream.of(F0), Stream.iterate(F1, fn -> fn.next()));
		}
	}


	/**
	 * Returns a single Fibonacci number at the specified index.
	 *
	 * @param index the index of the Fibonacci number
	 * @return the value of the Fibonacci number at the specified index
	 *
	 * @throws IllegalArgumentException if a non-negative index is provided
	 */
	public static BigInteger fibonacciNumber(long index) {
		if (index < 0) {
			throw new IllegalArgumentException("The index must be a nonnegative number.");
		}
	// TODO: Intentionally make a mistake here.
//		else if (index == 0) {
//			return FibonacciPair.F0.current;
//		}
//		else if (index == 1) {
//			return FibonacciPair.F1.current;
//		}

		/*
		 * Just skip numbers in the stream until we generate the one we want. Will
		 * this do what we want? Remember, f(n) = f(n - 1) + f(n - 2), or alternatively
		 * f(n - 2) = f(n - 1) + f(n)...
		 */
		// TODO: Intentionally make a mistake here.
//		return FibonacciPair.stream().skip(index - 1).findFirst().get().current;
		return FibonacciPair.stream().skip(index).findFirst().get().current;
	}

	/**
	 * Returns an infinite {@link Stream} of Fibonacci numbers as {@link BigInteger}
	 * objects starting with 0.
	 *
	 * @return an infinite stream of Fibonacci numbers
	 */
	public static Stream<BigInteger> fibonacciSequence() {
		return FibonacciPair.stream().map(fn -> fn.current);
	}

	/**
	 * Returns the sequence of Fibonacci numbers up to the specified index limit.
	 * For example, a limit of 10 will return 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 as
	 * a {@link List} of 10 {@link BigInteger} objects.
	 *
	 * @param limit the number of Fibonacci numbers to return (also the maximum index
	 * to consider when generating numbers)
	 * @return a list of Fibonacci numbers
	 */
	public static List<BigInteger> fibonacciNumbers(long limit) {
		Stream<BigInteger> stream = fibonacciSequence().limit(limit);
		List<BigInteger> list = stream.collect(Collectors.toList());
		return list;
	}

	public static void main(String[] args) {
		/*
		 * We could try and test our implementation here, but it is clunky.
		 * Instead, we will move to unit testing.
		 */
		System.out.println(fibonacciNumber(0));
		System.out.println(fibonacciNumber(1));
		System.out.println(fibonacciNumber(2));
		System.out.println(fibonacciNumber(3));
		System.out.println(fibonacciNumber(4));
		System.out.println(fibonacciNumber(5));

		FibonacciPair.stream().limit(5).forEach(System.out::println);
		fibonacciSequence().limit(5).forEach(System.out::println);
		System.out.println(fibonacciNumbers(10));
	}
}

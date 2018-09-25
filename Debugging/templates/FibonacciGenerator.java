import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

public class FibonacciGenerator {

	public static class FibonacciPair {
		public final long index;
		public final BigInteger previous;
		public final BigInteger current;

		public FibonacciPair(long index, BigInteger previous, BigInteger current) {
			this.index = index;
			this.previous = previous;
			this.current = current;
		}

		public FibonacciPair(long index, long previous, long current) {
			this(index, BigInteger.valueOf(previous), BigInteger.valueOf(current));
		}

		public FibonacciPair next() {
			// TODO
			return null;
		}

		@Override
		public String toString() {
			return "f(" + index + ") = " + current.toString();
		}

		// TODO

		public static Stream<FibonacciPair> stream() {
			// TODO
			return null;
		}
	}

	public static BigInteger fibonacciNumber(long index) {
		// TODO
		return null;
	}

	public static Stream<BigInteger> fibonacciSequence() {
		// TODO
		return null;
	}

	public static List<BigInteger> fibonacciNumbers(long limit) {
		// TODO
		return null;
	}

	public static void main(String[] args) {
		// TODO

//		System.out.println(fibonacciNumber(0));
//		System.out.println(fibonacciNumber(1));
//		System.out.println(fibonacciNumber(2));
//		System.out.println(fibonacciNumber(3));
//		System.out.println(fibonacciNumber(4));
//		System.out.println(fibonacciNumber(5));

//		FibonacciPair.stream().limit(5).forEach(System.out::println);
//		fibonacciSequence().limit(5).forEach(System.out::println);
//		System.out.println(fibonacciNumbers(10));
	}
}

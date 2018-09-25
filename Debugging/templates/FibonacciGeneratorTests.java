
// TODO
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

public class FibonacciGeneratorTests {

	// TODO
	public class FibonacciPairTests {

		public FibonacciGenerator.FibonacciPair test;

		public long expectedIndex;
		public BigInteger expectedPrevious;
		public BigInteger expectedCurrent;
		public BigInteger expectedNextCurrent;

		// TODO
		public void setup() {
			test = new FibonacciGenerator.FibonacciPair(1, 0, 1);

			expectedIndex = 2;
			expectedPrevious = BigInteger.valueOf(1);
			expectedCurrent = BigInteger.valueOf(1);
			expectedNextCurrent = BigInteger.valueOf(2);
		}

		// TODO

	}

	// TODO
	public class NextFibonacciPairTests {

		// TODO

	}

	// TODO
	public class FibonacciNumberTests {

		// TODO

		public void testFibonacci20() {
			BigInteger expected = BigInteger.valueOf(6765);
			assertEquals(expected, FibonacciGenerator.fibonacciNumber(20));
		}

		public void testFibonacci40() {
			BigInteger expected = new BigInteger("102334155");
			assertEquals(expected, FibonacciGenerator.fibonacciNumber(40));
		}

		public void testFibonacci100() {
			BigInteger expected = new BigInteger("354224848179261915075");
			assertEquals(expected, FibonacciGenerator.fibonacciNumber(100));
		}

		// TODO

	}

	// TODO
  // F0(0), F1(1), F2(1), F3(2), F4(3), F5(5), F6(8), F7(13), F8(21), F9(34);

	// @Override
	// public String toString() {
	// 	return "f(" + this.ordinal() + ") = " + this.value.toString();
	// }

	// TODO
	public class FibonacciNumbersTests {

		// TODO
		// List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)

	}

	// TODO
	public class ExceptionTests {

		// TODO
		// public final Duration TIMEOUT;

		// TODO
	}
}

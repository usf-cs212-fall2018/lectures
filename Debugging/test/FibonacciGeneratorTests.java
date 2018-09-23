
/*
 * You have to make sure you have the right assertions imported. JUnit 5 classes
 * are in the package org.junit.jupiter.api.Assertions.* whereas JUnit 4 classes
 * are in org.junit.Assert.* instead.
 *
 * You can configure Eclipse to let you import * here instead of each individual
 * assert statement requiring its own static import.
 */
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class FibonacciGeneratorTests {

	/*
	 * We can use the @Nested annotation to group tests within this file. This
	 * allows you to choose groups of tests to run, rather than choosing a single
	 * test versus the entire file of tests.
	 */
	@Nested
	public class FibonacciPairTests {

		// Setup any members required by the tests in this group.
		public FibonacciGenerator.FibonacciPair test;

		public long expectedIndex;
		public BigInteger expectedPrevious;
		public BigInteger expectedCurrent;
		public BigInteger expectedNextCurrent;

		/*
		 * Make sure these are properly re-initialized before every test. This makes
		 * sure each test gets a "clean" environment uncontaminated by other tests.
		 */

		@BeforeEach
		public void setup() {
			test = new FibonacciGenerator.FibonacciPair(1, 0, 1);

			// These values we can lookup on Wikipedia.
			// https://en.wikipedia.org/wiki/Fibonacci_number
			expectedIndex = 2;
			expectedPrevious = BigInteger.valueOf(1);
			expectedCurrent = BigInteger.valueOf(1);
			expectedNextCurrent = BigInteger.valueOf(2);
		}

		/*
		 * The @Test annotation indicates a specific JUnit test.
		 */
		@Test
		public void testNextIndex() {
			// Specify the expected result.
			long expected = expectedIndex;

			// Specify the actual result.
			long actual = test.next().index;

			// Every test must have an assertion. The expected value always goes
			// first.
			assertEquals(expected, actual);
		}

		/*
		 * You usually place each thing you are testing in a separate test method
		 * for a strict "unit" test. However you could technically place all of
		 * these tests in the same method, but the then methods can fail for more
		 * than one reason. This can make debugging harder at times.
		 */

		@Test
		public void testNextPrevious() {
			BigInteger expected = expectedPrevious;
			BigInteger actual = test.next().previous;

			/*
			 * You can add a custom message to the output if the test fails. This
			 * message is NOT output if the test passes.
			 */
			assertEquals(expected, actual, "The previous value was wrong!");
		}

		@Test
		public void testNextCurrent() {
			BigInteger expected = expectedCurrent;
			BigInteger actual = test.next().current;

			/*
			 * If you have to do something time/space inefficient to generate the
			 * debug output, put it in a lambda expression. It will only run that code
			 * if the test fails (i.e. this version is lazy, the other was eager).
			 */
			assertEquals(
					expected, actual, () -> "This string " + "concatenation is"
							+ "only performed if " + " the test fails."
					);
		}

		// You can also customize the display name of the test.

		@Test
		@DisplayName("Test Next Twice")
		public void testNextNext() {
			BigInteger expected = expectedNextCurrent;
			BigInteger actual = test.next().next().current;
			assertEquals(expected, actual);
		}
	}

	/*
	 * The plus side of manually specifying each and every test with the @Test
	 * annotation is that you can easily run one test at a time. However, for many
	 * tests of the same form, it can be cumbersome. What if we wanted to do
	 * everything above, but for a different base case?
	 *
	 * Well... @Test annotations are inherited!
	 */

	@Nested
	public class NextFibonacciPairTests extends FibonacciPairTests {

		@Override
		@BeforeEach
		public void setup() {
			test = new FibonacciGenerator.FibonacciPair(2, 1, 1);

			expectedIndex = 3;
			expectedPrevious = BigInteger.valueOf(1);
			expectedCurrent = BigInteger.valueOf(2);
			expectedNextCurrent = BigInteger.valueOf(3);
		}

		/*
		 * Notice now, however, that we do not have individual test methods that we
		 * can right-click in Eclipse to run individually. But, you can still
		 * manually create a "Run Configuration" to run the inherited test methods
		 * individually.
		 */
	}

	@Nested
	public class FibonacciNumberTests {

		/*
		 * Lets test a few big Fibonacci numbers. There are some large values
		 * online at https://oeis.org/A000045/list and at:
		 * https://onlinelibrary.wiley.com/doi/pdf/10.1002/9781118033067.app3
		 */

		@Test
		public void testFibonacci20() {
			BigInteger expected = BigInteger.valueOf(6765);
			assertEquals(expected, FibonacciGenerator.fibonacciNumber(20));
		}

		@Test
		public void testFibonacci40() {
			BigInteger expected = new BigInteger("102334155");
			assertEquals(expected, FibonacciGenerator.fibonacciNumber(40));
		}

		@Test
		public void testFibonacci100() {
			BigInteger expected = new BigInteger("354224848179261915075");
			assertEquals(expected, FibonacciGenerator.fibonacciNumber(100));
		}

		/*
		 * What if you wanted to specify a LOT of test cases, like making sure our
		 * fibonacciNumber() method produced the correct first 10 values. That is a
		 * lot of little tests to write. Instead, we can use parameterized tests to
		 * generate tests from an expected "value source".
		 *
		 * THIS IS AN EXPERIMENTAL FEATURE. You may need to update your version of
		 * Eclipse and update its installed packages for this code to work.
		 */

		/*
		 * There are lots of value sources. If you need to specify more than one
		 * parameter and those parameters have different types, consider a CSV source
		 * or creating a custom enum object to store the parameters (see below).
		 */

		@ParameterizedTest(name = "{0}") // use toString() as name
		@EnumSource(FibonacciNumber.class)
		void testFibonacciNumbers(FibonacciNumber number) {
			BigInteger expected = number.value;
			BigInteger actual = FibonacciGenerator.fibonacciNumber(number.ordinal());
			assertEquals(expected, actual);
		}

		/*
		 * This is pretty neat, but comes with downsides. If you run this entire
		 * group of tests, then you can still select a single test in the JUnit view
		 * to run it individually, but you cannot setup a Run Configuration in
		 * Eclipse manually to run an individual test anymore.
		 *
		 * Instead, you can limit which enums are considered. Try this:
		 * @EnumSource(value = FIBONACCI_NUMBERS.class, names = { "F3", "F4" })
		 */
	}

	public static enum FibonacciNumber {
		/*
		 * The "ordinal" of the enum is set automatically starting at 0 and
		 * incrementing by one each time. We'll use this for the index, and then
		 * call the enum constructor to set the expected value for that index.
		 */
		F0(0), F1(1), F2(1), F3(2), F4(3), F5(5), F6(8), F7(13), F8(21), F9(34);

		public final BigInteger value;

		/*
		 * Enum constructors must be private. The only way to create an enum instance
		 * is using the initialization list above.
		 */
		private FibonacciNumber(long value) {
			this.value = BigInteger.valueOf(value);
		}

		@Override
		public String toString() {
			return "f(" + this.ordinal() + ") = " + this.value.toString();
		}
	}

	@Nested
	public class FibonacciNumbersTests {
		/*
		 * If you need more flexibility than provided by the parameterized tests,
		 * you could use dynamic tests instead, which dynamically generate the test
		 * methods when compiled and run.
		 *
		 * THIS IS AN EXPERIMENTAL FEATURE. You may need to update your version of
		 * Eclipse and update its installed packages for this code to work.
		 */

		/*
		 * We want to make sure the lists generated are correct. We can specify one
		 * list of the first 10 expected values and use sublists for other tests. We
		 * also want to make sure both the size of the lists and the values in those
		 * lists are correct.
		 */

		/*
		 * First you need to think about each test you want to generate.
		 */

		public DynamicTest generateSizeTest(int index) {
			List<BigInteger> actual = FibonacciGenerator.fibonacciNumbers(index);
			return dynamicTest("size for limit = " + index, () -> {
				assertEquals(index, actual.size());
			});
		}

		public DynamicTest generateContentTest(int index, List<BigInteger> values) {
			List<BigInteger> expected = values.subList(0, index);
			List<BigInteger> actual = FibonacciGenerator.fibonacciNumbers(index);
			return dynamicTest("content for limit = " + index, () -> {
				assertEquals(expected, actual);
			});
		}

		/*
		 * Now we need to specify the values and generate the stream of tests.
		 */
		@TestFactory
		public Stream<DynamicTest> testFibonacciNumber() {
			List<BigInteger> expected = List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
					.stream()
					.map(item -> BigInteger.valueOf(item))
					.collect(Collectors.toList());

			Stream<DynamicTest> testSizes = IntStream.range(0,  expected.size())
					.mapToObj(i -> generateSizeTest(i));

			Stream<DynamicTest> testContents = IntStream.range(0,  expected.size())
					.mapToObj(i -> generateContentTest(i, expected));

			return Stream.concat(testSizes, testContents);
		}

		/*
		 * Again, this is neat but has more downsides. Since the tests are
		 * dynamically generated, it is difficult to select just one individual test
		 * to run at a specific time. You can select a single method, but not the
		 * individual tests generated by that method until you have compiled and run
		 * the method at least once already. Then, you can select the individual tests
		 * in the JUnit view on Eclipse.
		 */
	}

	/*
	 * You want to make sure things work as expected, AND that they fail as
	 * expected too. While it is possible to extend the Fibonacci sequence into
	 * the negative numbers, that is not supported by our code. Therefore, there
	 * should be some indication of this when negative numbers are used.
	 *
	 * This also tends to be where infinite loops can be triggered, so we will
	 * start using timeouts in these tests.
	 */

	@Nested
	public class ExceptionTests {

		public final Duration TIMEOUT = Duration.ofMillis(100);

		@Test
		public void testNegativeFibonacciNumber() {
			// This is the core action we are testing.
			Executable action = () -> {
				FibonacciGenerator.fibonacciNumber(-10);
			};

			// We want to assert this action DOES throw an exception
			Executable wrapper = () -> {
				assertThrows(IllegalArgumentException.class, action);
			};

			// But, in case this breaks things, we don't want to wait forever.
			assertTimeout(TIMEOUT, wrapper);
		}

		@Test
		public void testNegativeFibonacciNumbers() {
			// We do not need to separate all of the lambda expressions...
			assertTimeout(TIMEOUT, () -> {
				assertThrows(IllegalArgumentException.class, () -> {
					FibonacciGenerator.fibonacciNumbers(-1);
				});
			});
		}

		@Test
		public void testZeroFibonacciNumbers() {
			// Zero should work without exceptions.
			assertTimeout(TIMEOUT, () -> {
				FibonacciGenerator.fibonacciNumbers(0);
			});
		}
	}
}

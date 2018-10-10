import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demonstrates the time difference between using the synchronized keyword and a
 * read/write lock for an IndexedSet object.
 *
 * @see IndexedSet
 * @see SynchronizedSet
 * @see ConcurrentSet
 */
public class SetDemo {
	private static final Logger log = LogManager.getLogger();

	/**
	 * Generates a list of generic data elements.
	 *
	 * @param size number of data elements to generate
	 * @return list of data elements
	 */
	public static List<Integer> generateData(int size) {
		return new Random().ints(size).boxed().collect(Collectors.toList());
	}

	/**
	 * Returns the number of nanoseconds that passed from one add and two copy
	 * operations.
	 *
	 * @param source      source data to add to destination set
	 * @param destination destination set (should be thread-safe)
	 * @return nanoseconds passed
	 */
	public static long timeMulti(List<Integer> source, IndexedSet<Integer> destination) {
		long time = System.nanoTime();

		Thread adder = new Thread(() -> {
			destination.addAll(source);
			log.debug("destination size is {}", destination.size());
		});

		Thread copy1 = new Thread(() -> {
			Set<Integer> sorted = destination.sortedCopy();
			log.debug("sorted size is {}", sorted.size());
		});

		Thread copy2 = new Thread(() -> {
			Set<Integer> unsorted = destination.unsortedCopy();
			log.debug("unsorted size is {}", unsorted.size());
		});

		adder.setPriority(Thread.MAX_PRIORITY);
		copy1.setPriority(Thread.NORM_PRIORITY);
		copy2.setPriority(Thread.NORM_PRIORITY);

		adder.start();
		copy1.start();
		copy2.start();

		try {
			adder.join();
			copy1.join();
			copy2.join();
		}
		catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}

		return System.nanoTime() - time;
	}

	/**
	 * Returns the number of nanoseconds that passed from one add and two copy
	 * operations.
	 *
	 * @param source      source data to add to destination set
	 * @param destination destination set (should be thread-safe)
	 * @return nanoseconds passed
	 */
	public static long timeSingle(List<Integer> source, IndexedSet<Integer> destination) {
		long time = System.nanoTime();

		destination.addAll(source);
		log.debug("destination size is {}", destination.size());

		Set<Integer> sorted = destination.sortedCopy();
		log.debug("sorted size is {}", sorted.size());

		Set<Integer> unsorted = destination.unsortedCopy();
		log.debug("unsorted size is {}", unsorted.size());

		return System.nanoTime() - time;
	}

	/**
	 * Roughly demonstrates runtime different between using synchronized and a
	 * read/write lock when there are more than one large read operations. Note:
	 * not an accurate benchmark, but you get the idea.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		// TURN OFF LOGGING BEFORE RUNNING THIS!

		List<Integer> data = generateData(1000000);
		log.debug("data size is {}", data.size());

		// There may be duplicates that get removed by adding to the sets.

		IndexedSet<Integer> set1 = new IndexedSet<>();
		SynchronizedSet<Integer> set2 = new SynchronizedSet<>();
		ConcurrentSet<Integer> set3 = new ConcurrentSet<>();

		double time1 = timeSingle(data, set1) / (double) 1000000000;
		double time2 = timeMulti (data, set2) / (double) 1000000000;
		double time3 = timeMulti (data, set3) / (double) 1000000000;

		System.out.printf("Indexed     : %.5f seconds%n", time1);
		System.out.printf("Synchronized: %.5f seconds%n", time2);
		System.out.printf("Concurrent  : %.5f seconds%n", time3);
		System.out.println();

		double speed1 = time1 / time2;
		double speed2 = time2 / time3;
		double speed3 = time1 / time3;

		String format = "%-12s is %.4fx %s than %-12s%n";
		System.out.printf(format, "Synchronized", speed1, speed1 > 0 ? "faster" : "slower", "Indexed");
		System.out.printf(format, "Concurrent",   speed3, speed3 > 0 ? "faster" : "slower", "Indexed");
		System.out.printf(format, "Concurrent",   speed2, speed2 > 0 ? "faster" : "slower", "Synchronized");
	}
}

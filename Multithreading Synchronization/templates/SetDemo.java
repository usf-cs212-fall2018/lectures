import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDemo {
	private static final Logger log = LogManager.getLogger();

	public static List<Integer> generateData(int size) {
		return null; // TODO
	}

	public static long timeMulti(List<Integer> source, IndexedSet<Integer> destination) {
		long time = System.nanoTime();

		Thread adder = null; // TODO

		Thread copy1 = null; // TODO

		Thread copy2 = null; // TODO

		adder.setPriority(Thread.MAX_PRIORITY);
		copy1.setPriority(Thread.NORM_PRIORITY);
		copy2.setPriority(Thread.NORM_PRIORITY);

		// TODO

		return System.nanoTime() - time;
	}

	public static long timeSingle(List<Integer> source, IndexedSet<Integer> destination) {
		long time = System.nanoTime();

		// TODO

		return System.nanoTime() - time;
	}

	public static void main(String[] args) {

		// TURN OFF LOGGING BEFORE RUNNING THIS!

		List<Integer> data = generateData(1000000);
		log.debug("data size is {}", data.size());

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

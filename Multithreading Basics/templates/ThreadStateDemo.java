import java.util.Arrays;

public class ThreadStateDemo {

	public static final String FORMAT =
			"[%-11s] Run by: %-8s  Parent: %s is %-8s  Worker: %s is %s";

	private Thread parentThread;
	private Thread workerThread;

	public ThreadStateDemo() throws InterruptedException {
		// TODO
	}

	public static void calculate(int size) {
		double[] junk = new double[size];

		for (int i = 0; i < junk.length; i++) {
			junk[i] = Math.random();
		}

		Arrays.sort(junk);
	}

	public Object[] params(String message) {
		return new String[] { message, Thread.currentThread().getName(),
				parentThread.getName(), parentThread.getState().toString(),
				workerThread.getName(), workerThread.getState().toString() };
	}

	public void output(String message) {
		System.out.printf(FORMAT, params(message));
		System.out.println();
	}

	// TODO

	public static void main(String[] args) throws InterruptedException {
		new ThreadStateDemo();
	}
}

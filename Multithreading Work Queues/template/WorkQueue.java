import java.util.LinkedList;

public class WorkQueue {

	private final PoolWorker[] workers;
	private final LinkedList<Runnable> queue;

	public static final int DEFAULT = 5;

	public WorkQueue() {
		this(DEFAULT);
	}

	public WorkQueue(int threads) {
		// TODO
		workers = null;
		queue = null;
	}

	public void execute(Runnable r) {
		// TODO
	}

	public void shutdown() {
		// TODO
	}

	public int size() {
		return workers.length;
	}

	private class PoolWorker extends Thread {

		@Override
		public void run() {

			// TODO

		}
	}
}

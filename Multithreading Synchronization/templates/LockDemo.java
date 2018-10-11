@SuppressWarnings("unused")
public class LockDemo {

	private final Thread worker1;
	private final Thread worker2;

	private final static Object staticKey = new Object();
	private final Object instanceKey1;
	private final Object instanceKey2;

	public LockDemo(String name) {
		instanceKey1 = new Object();
		instanceKey2 = new Object();

		worker1 = new Worker(instanceKey1);
		worker2 = new Worker(instanceKey2);

		worker1.setName(name + "1");
		worker2.setName(name + "2");

		worker1.start();
		worker2.start();
	}

	public void joinAll() throws InterruptedException {
		worker1.join();
		worker2.join();
	}

	private class Worker extends Thread {

		private final Object key;

		public Worker(Object key) {
			this.key = key;
		}

		@Override
		public void run() {
			// TODO
			// System.out.println(this.getName() + " Lock?: " + Thread.holdsLock(key));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		LockDemo demo1 = new LockDemo("A");
		LockDemo demo2 = new LockDemo("B");

		// TODO

		// System.out.println("A1 State: " + demo1.worker1.getState());
		// System.out.println("A2 State: " + demo1.worker2.getState());
		// System.out.println("B1 State: " + demo2.worker1.getState());
		// System.out.println("B2 State: " + demo2.worker2.getState());

		demo1.joinAll();
		demo2.joinAll();

	}
}

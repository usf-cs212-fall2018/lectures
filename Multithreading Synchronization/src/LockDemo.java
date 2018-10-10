/**
 * This class demonstrates the impact different types of lock/key objects have on
 * whether multiple threads may run synchronized blocks of code simultaneously.
 */
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

		/*
		 * Comment in/out different examples and see how the behavior changes.
		 */

		// TF TF TF TF
//		worker1 = new Worker(LockDemo.class);
//		worker2 = new Worker(LockDemo.Worker.class);

		// TF TF TF TF
//		worker1 = new Worker(staticKey);
//		worker2 = new Worker(staticKey);

		// TF TF TF TF
//		worker1 = new Worker(LockDemo.class);
//		worker2 = new Worker(LockDemo.class);

		// TT FF TT FF
//		worker1 = new Worker(instanceKey1);
//		worker2 = new Worker(instanceKey1);

		// TT FF TT FF
//		worker1 = new Worker(this);
//		worker2 = new Worker(this);

		// TT TT FF FF
//		worker1 = new Worker(new Object());
//		worker2 = new Worker(new Object());

		// TT TT FF FF
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
			// this.key = new Object();
		}

		@Override
		public void run() {
//			 synchronized(this) {
//				 System.out.println(this.getName() + " Lock?: " + Thread.holdsLock(this));
			synchronized (key) {
				System.out.println(this.getName() + " Lock?: " + Thread.holdsLock(key));

				try {
					// This thread will keep its lock while sleeping!
					Thread.sleep(1000);
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			System.out.println(this.getName() + " Lock?: " + Thread.holdsLock(key));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		LockDemo demo1 = new LockDemo("A");
		LockDemo demo2 = new LockDemo("B");

		// Wait a little bit, hopefully the threads get a chance to get their locks
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		System.out.println("A1 State: " + demo1.worker1.getState());
		System.out.println("A2 State: " + demo1.worker2.getState());
		System.out.println("B1 State: " + demo2.worker1.getState());
		System.out.println("B2 State: " + demo2.worker2.getState());

		demo1.joinAll();
		demo2.joinAll();

		/*
		 * We have the following threads TRYING to run at the same time:
		 *
		 * +---LockDemo A---+    +---LockDemo B---+
		 * | +-A1-+  +-A2-+ |    | +-B1-+  +-B2-+ |
		 * | |    |  |    | |    | |    |  |    | |
		 * | +----+  +----+ |    | +----+  +----+ |
		 * +----------------+    +----------------+
		 *
		 * Whether A1, A2, B1, B2 are able to run simultaneously (e.g. threads are
		 * able to enter the "locked rooms" setup by each thread) depends on the
		 * type of lock/key used. If there are not enough "keys" then threads will
		 * become blocked.
		 */
	}
}

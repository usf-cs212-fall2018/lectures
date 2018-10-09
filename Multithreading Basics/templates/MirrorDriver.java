import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MirrorDriver {

	private static final Logger logger = LogManager.getLogger();
	public static final String EXIT = "exit";

	public MirrorDriver() {
		// TODO
	}

	public class ProducerThread extends Thread {

		public ProducerThread() {
			super();
			// TODO
		}

		@Override
		public void run() {
			while (true) {
				// TODO

				try {
					// TODO
				}
				catch (Exception ex) {
					logger.debug("ProducerThread.run() encounterd exception!");
					break;
				}
			}
		}
	}

	public class ConsumerThread extends Thread {

		public ConsumerThread() {
			super();
		}

		@Override
		public void run() {
			while (true) {
				try {
					// TODO
					throw new InterruptedException();
				}
				catch (InterruptedException ex) {
					logger.debug("ConsumerThread.run() interrupted!");
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		new MirrorDriver();
	}
}

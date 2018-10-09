import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormattedMessage;

/**
 * Demonstrates simple changes in thread state for a main thread and a single
 * worker thread. Same as {@link ThreadStateDemo} except this class uses log4j2
 * for the output.
 */
public class ThreadStateLogged extends ThreadStateDemo {

	private static final Logger log = LogManager.getLogger();

	public ThreadStateLogged() throws InterruptedException {
		super();
	}

	@Override
	public void output(String message) {
		// See https://logging.apache.org/log4j/2.x/manual/messages.html
		// Use ParameterizedMessage when possible (it is faster than this)
		log.debug(new StringFormattedMessage(FORMAT, params(message)));
	}

	/**
	 * Starts the simple thread state demo.
	 *
	 * @param args unused
	 * @throws InterruptedException if a thread is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		new ThreadStateLogged();
	}
}

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormattedMessage;

public class ThreadStateLogged extends ThreadStateDemo {

	private static final Logger log = LogManager.getLogger();

	public ThreadStateLogged() throws InterruptedException {
		super();
	}

	@Override
	public void output(String message) {
		log.debug(new StringFormattedMessage(FORMAT, params(message)));
	}

	public static void main(String[] args) throws InterruptedException {
		new ThreadStateLogged();
	}
}

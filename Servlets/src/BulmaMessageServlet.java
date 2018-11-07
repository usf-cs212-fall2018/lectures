import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@SuppressWarnings("serial")
public class BulmaMessageServlet extends HttpServlet {
	private static final String TITLE = "Messages";
	private static Logger log = Log.getRootLogger();

	private ConcurrentLinkedQueue<String> messages;

	public BulmaMessageServlet() {
		super();
		messages = new ConcurrentLinkedQueue<>();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		log.info("MessageServlet ID " + this.hashCode() + " handling GET request.");

		PrintWriter out = response.getWriter();

		out.printf("<!DOCTYPE html>%n");
		out.printf("<html>%n");
		out.printf("<head>%n");
		out.printf("	<meta charset=\"utf-8\">%n");
		out.printf("	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">%n");
		out.printf("	<title>%s</title>%n", TITLE);
		out.printf("	<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.2/css/bulma.min.css\">%n");
		out.printf("	<script defer src=\"https://use.fontawesome.com/releases/v5.3.1/js/all.js\"></script>%n");
		out.printf("</head>%n");
		out.printf("%n");
		out.printf("<body>%n");
		out.printf("	<section class=\"hero is-primary is-bold\">%n");
		out.printf("	  <div class=\"hero-body\">%n");
		out.printf("	    <div class=\"container\">%n");
		out.printf("	      <h1 class=\"title\">%n");
		out.printf("	        Message Board%n");
		out.printf("	      </h1>%n");
		out.printf("	      <h2 class=\"subtitle\">%n");
		out.printf("					<i class=\"fas fa-calendar-alt\"></i>%n");
		out.printf("					&nbsp;Updated %s%n", getDate());
		out.printf("	      </h2>%n");
		out.printf("	    </div>%n");
		out.printf("	  </div>%n");
		out.printf("	</section>%n");
		out.printf("%n");
		out.printf("	<section class=\"section\">%n");
		out.printf("		<div class=\"container\">%n");
		out.printf("			<h2 class=\"title\">Messages</h2>%n");
		out.printf("%n");

		if (messages.isEmpty()) {
			out.printf("				<p>No messages.</p>%n");
		}
		else {
			for (String message : messages) {
				out.printf("				<div class=\"box\">%n");
				out.printf(message);
				out.printf("				</div>%n");
				out.printf("%n");
			}
		}

		out.printf("			</div>%n");
		out.printf("%n");
		out.printf("		</div>%n");
		out.printf("	</section>%n");
		out.printf("%n");
		out.printf("	<section class=\"section\">%n");
		out.printf("		<div class=\"container\">%n");
		out.printf("			<h2 class=\"title\">Add Message</h2>%n");
		out.printf("%n");
		out.printf("			<form method=\"%s\" action=\"%s\">%n", "POST", request.getServletPath());
		out.printf("				<div class=\"field\">%n");
		out.printf("					<label class=\"label\">Name</label>%n");
		out.printf("					<div class=\"control has-icons-left\">%n");
		out.printf("						<input class=\"input\" type=\"text\" name=\"%s\" placeholder=\"Enter your name here.\">%n", "username");
		out.printf("						<span class=\"icon is-small is-left\">%n");
		out.printf("							<i class=\"fas fa-user\"></i>%n");
		out.printf("						</span>%n");
		out.printf("					</div>%n");
		out.printf("				</div>%n");
		out.printf("%n");
		out.printf("				<div class=\"field\">%n");
		out.printf("				  <label class=\"label\">Message</label>%n");
		out.printf("				  <div class=\"control\">%n");
		out.printf("				    <textarea class=\"textarea\" name=\"%s\" placeholder=\"Enter your message here.\"></textarea>%n", "message");
		out.printf("				  </div>%n");
		out.printf("				</div>%n");
		out.printf("%n");
		out.printf("				<div class=\"control\">%n");
		out.printf("			    <button class=\"button is-primary\" type=\"submit\">%n");
		out.printf("						<i class=\"fas fa-comment\"></i>%n");
		out.printf("						&nbsp;%n");
		out.printf("						Post Message%n");
		out.printf("					</button>%n");
		out.printf("			  </div>%n");
		out.printf("			</form>%n");
		out.printf("		</div>%n");
		out.printf("	</section>%n");
		out.printf("%n");
		out.printf("	<footer class=\"footer\">%n");
		out.printf("	  <div class=\"content has-text-centered\">%n");
		out.printf("	    <p>%n");
		out.printf("	      This request was handled by thread %s.%n", Thread.currentThread().getName());
		out.printf("	    </p>%n");
		out.printf("	  </div>%n");
		out.printf("	</footer>%n");
		out.printf("</body>%n");
		out.printf("</html>%n");

		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		log.info("MessageServlet ID " + this.hashCode() + " handling POST request.");

		String username = request.getParameter("username");
		String message = request.getParameter("message");

		username = username == null ? "anonymous" : username;
		message = message == null ? "" : message;

		// Avoid XSS attacks using Apache Commons Text
		// Comment out if you don't have this library installed
		username = StringEscapeUtils.escapeHtml4(username);
		message = StringEscapeUtils.escapeHtml4(message);

		String formatted = String.format(
				"					<i class=\"fas fa-quote-left has-text-grey-light\"></i> %s <i class=\"fas fa-quote-right has-text-grey-light\"></i>%n" +
				"					<p class=\"has-text-grey is-size-7 has-text-right\"><em><i class=\"fas fa-user has-text-grey-light\"></i> Posted by %s at %s.</em></p>%n",
				message,
				username,
				getDate()
		);


		// Keep in mind multiple threads may access at once
		messages.add(formatted);

		// Only keep the latest 5 messages
		if (messages.size() > 5) {
			String first = messages.poll();
			log.info("Removing message: " + first);
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(request.getServletPath());
	}

	private static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}

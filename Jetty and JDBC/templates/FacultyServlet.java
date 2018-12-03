import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@SuppressWarnings("unused")
public class FacultyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Log.getLog();

	private final DatabaseConnector connector;

	private final String SQL_SELECT;
	private final String HTML_HEADER;
	private final String HTML_ROW;
	private final String HTML_FORM;
	private final String HTML_FOOTER;

	public FacultyServlet(DatabaseConnector connector) throws IOException {
		this.connector = connector;

		SQL_SELECT  = loadFile("sql",  "SELECT");
		HTML_HEADER = loadFile("html", "header");
		HTML_ROW    = loadFile("html", "row");
		HTML_FORM   = loadFile("html", "form");
		HTML_FOOTER = loadFile("html", "footer");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.printf(HTML_HEADER);

		String sql = craftSQL(request);
		log.info("SQL: {}", sql);

//					String name  = escape(results, "name");
//					String email = escape(results, "email");
//					String twitter = escape(results, "twitter");
//					String courses = escape(results, "courses");

//					out.printf(HTML_ROW, name, email, twitter, courses);
//					out.println();

		// TODO

		out.printf(HTML_FOOTER, Thread.currentThread().getName(), getLongDate());
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	private String craftSQL(HttpServletRequest request) {
		StringBuilder sql = new StringBuilder(SQL_SELECT);

		// TODO

		sql.append(System.lineSeparator());
		sql.append("GROUP BY faculty_names.usfid");

		// TODO

		sql.append(System.lineSeparator());
		sql.append("ORDER BY last ASC");

		sql.append(";");

		return sql.toString();
	}

	private static boolean filterTwitter(HttpServletRequest request) {
		try {
			return request.getParameter("twitter").equalsIgnoreCase("on");
		}
		catch (Exception e) {
			return false;
		}
	}

	private static boolean filterColumn(HttpServletRequest request) {
		try {
			return !request.getParameter("filter").trim().isEmpty();
		}
		catch (Exception e) {
			return false;
		}
	}

	private static String getField(HttpServletRequest request) {
		try {
			String field = request.getParameter("field").toLowerCase();

			switch (field) {
				case "last":
				case "first":
				case "courses":
					return field;
				default:
					log.warn("Suspicious field: {}", field);
					return "last";
			}
		}
		catch (Exception e) {
			return "last";
		}
	}

	protected static String escape(ResultSet results, String column) throws SQLException {
		return StringEscapeUtils.escapeHtml4(results.getString(column));
	}

	private static String loadFile(String type, String name) throws IOException {
		String filename = String.format("%s.%s", name, type);
		Path path = Paths.get(type, filename);
		byte[] bytes = Files.readAllBytes(path);
	  return new String(bytes, StandardCharsets.UTF_8);
	}

	public static String getLongDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}

	public static boolean nonEmpty(String text) {
		return text != null && !text.trim().isEmpty();
	}
}

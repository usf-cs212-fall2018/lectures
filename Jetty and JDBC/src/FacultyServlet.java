import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

/**
 * Demonstrates how to interact with our MariaDB database server on campus in
 * Jetty.
 */
public class FacultyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Log.getLog();

	private final DatabaseConnector connector;

	// SQL TEMPLATES
	private final String SQL_SELECT;

	// HTML TEMPLATES
	private final String HTML_HEADER;
	private final String HTML_ROW;
	private final String HTML_FORM;
	private final String HTML_FOOTER;

	/**
	 * Initializes this servlet. Requires an already established database connector.
	 * Will fail to initialize if template files are not found.
	 *
	 * @param connector
	 * @throws IOException
	 */
	public FacultyServlet(DatabaseConnector connector) throws IOException {
		this.connector = connector;

		// Load all of the SQL and HTML templates.
		SQL_SELECT  = loadFile("sql",  "SELECT");
		HTML_HEADER = loadFile("html", "header");
		HTML_ROW    = loadFile("html", "row");
		HTML_FORM   = loadFile("html", "form");
		HTML_FOOTER = loadFile("html", "footer");
	}

	/*
	 * We will use the protected keyword here to match the HttpServlet class:
	 * https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.printf(HTML_HEADER);

		String sql = craftSQL(request);
		log.info("SQL: {}", sql);

		try (
				Connection db = connector.getConnection();
				PreparedStatement statement = db.prepareStatement(sql);
		) {
			// add filter query if necessary
			if (filterColumn(request)) {
				String filter = request.getParameter("filter");
				statement.setString(1, filter);
			}

			try (ResultSet results = statement.executeQuery()) {
				while (results.next()) {
					String name  = escape(results, "name");
					String email = escape(results, "email");
					String twitter = escape(results, "twitter");
					String courses = escape(results, "courses");

					if (nonEmpty(twitter)) {
						twitter = String.format("<a href=\"http://twitter.com/%1$s\">@%1$s</a>", twitter);
					}
					else {
						twitter = "&nbsp;";
					}

					out.printf(HTML_ROW, name, email, twitter, courses);
					out.println();
				}
			}
		}
		catch (SQLException e) {

			log.warn(e);
		}

		out.printf(HTML_FORM, request.getServletPath());
		out.printf(HTML_FOOTER, Thread.currentThread().getName(), getLongDate());
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	/**
	 * Crafts the prepared SQL statement. Uses "?" as a placeholder for potentially
	 * unsafe input from the request.
	 *
	 * @param request the HTTP request
	 * @return a string to be used in a prepared SQL statement
	 */
	private String craftSQL(HttpServletRequest request) {
		StringBuilder sql = new StringBuilder(SQL_SELECT);

		// Determine whether to filter by Twitter accounts
		if (filterTwitter(request)) {
			sql.append(System.lineSeparator());
			sql.append("WHERE twitterid IS NOT NULL");
		}

		String field = getField(request);
		boolean filter = filterColumn(request);

		// Determine whether to filter by name
		if (filter && !field.equals("courses")) {
			/*
			 * We can't whitelist possible queries. To avoid SQL injection attacks,
			 * we will use prepared statements instead. Anywhere something unsafe will
			 * be inserted into the statement, add a "?" instead.
			 */
			sql.append(System.lineSeparator());
			sql.append("WHERE ");
			sql.append(field);
			sql.append(" LIKE ?");
		}

		// Add group by clause
		sql.append(System.lineSeparator());
		sql.append("GROUP BY faculty_names.usfid");

		// Determine whether to filter by course (must happen post group)
		if (filter && field.equals("courses")) {
			sql.append(System.lineSeparator());
			sql.append("HAVING ");
			sql.append(field);
			sql.append(" LIKE ?");
		}

		// Add order by clause
		sql.append(System.lineSeparator());
		sql.append("ORDER BY last ASC");

		// Add final semi colon
		sql.append(";");

		return sql.toString();
	}

	/**
	 * Returns whether the HTTP request parameters indicate the results should be
	 * filtered by twitter accounts.
	 *
	 * @param request the HTTP request
	 * @return true of the appropriate parameter is provided
	 */
	private static boolean filterTwitter(HttpServletRequest request) {
		try {
			return request.getParameter("twitter").equalsIgnoreCase("on");
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns whether the HTTP request parameters indicate the results should be
	 * filtered by column.
	 *
	 * @param request the HTTP request
	 * @return true of the appropriate parameter is provided
	 */
	private static boolean filterColumn(HttpServletRequest request) {
		try {
			return !request.getParameter("filter").trim().isEmpty();
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * To protect against XSS attacks, field values fetched from the query
	 * string of the HTTP request will be whitelisted. A default value will be
	 * returned if any invalid values are detected.
	 *
	 * @param request the HTTP request
	 * @return a whitelisted field value
	 */
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

	/**
	 * Attempts to retrieve data from a result set and escape any special
	 * characters returned. Useful for avoiding XSS attacks.
	 *
	 * @param results the set of results returned from a database
	 * @param column the name of the column to fetch
	 * @return escaped text (or null if result was null)
	 * @throws SQLException
	 */
	protected static String escape(ResultSet results, String column) throws SQLException {
		return StringEscapeUtils.escapeHtml4(results.getString(column));
	}

	/**
	 * Loads a file from the current directory. Used to load format strings from
	 * file (either SQL or HTML templates).
	 *
	 * @param type the type of file to load (either sql or html)
	 * @param name the name of the file (without extension)
	 * @return the file as a single String
	 * @throws IOException
	 */
	private static String loadFile(String type, String name) throws IOException {
		String filename = String.format("%s.%s", name, type);
		Path path = Paths.get(type, filename);
		byte[] bytes = Files.readAllBytes(path);
	  return new String(bytes, StandardCharsets.UTF_8);
	}

	/**
	 * Returns the current date in long format.
	 * @return the current date in long format
	 */
	public static String getLongDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}

	/**
	 * Returns whether the text is not null and not the empty string.
	 *
	 * @param text the text to test
	 * @return true if the text is not null and not the empty string
	 */
	public static boolean nonEmpty(String text) {
		return text != null && !text.trim().isEmpty();
	}
}

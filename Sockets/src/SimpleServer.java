import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Demonstrates server-side and client-side sockets.
 *
 * @see SimpleServer
 * @see SimpleClient
 */
public class SimpleServer {
	public static final int PORT = 5554;
	public static final String EOT = "EOT";
	public static final String EXIT = "SHUTDOWN";

	public static void main(String[] args) throws IOException {
		String line = null;

		try (ServerSocket server = new ServerSocket(PORT);) {
			// keep looping to accept clients
			while (!server.isClosed()) {
				System.out.println("Server: Waiting for connection...");

				try (
						Socket socket = server.accept();
						InputStreamReader input = new InputStreamReader(socket.getInputStream());
						BufferedReader reader = new BufferedReader(input);
				) {
					// while client is connected
					while (!socket.isClosed()) {
						// read line from client socket
						line = reader.readLine();
						System.out.println("Server: " + line);

						// check for shutdown cases
						if (line.equals(EOT)) {
							System.out.println("Server: Closing socket.");
							socket.close();
						}
						else if (line.equals(EXIT)) {
							System.out.println("Server: Shutting down.");
							socket.close();
							server.close();
						}
					}
				}

				System.out.println("Server: Client disconnected.");
			}
		}
	}
}

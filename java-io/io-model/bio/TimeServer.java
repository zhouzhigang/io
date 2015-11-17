import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Blocking I/O server.
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port  = 8080;
        // get port from argument
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
            }
        }

        ServerSocket server = null;
        try {
            // create server socket
            server = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            while (true) {
                // blocking on accept
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}

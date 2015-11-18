import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Non-Blocking I/O server using ExecutorService.
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

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}

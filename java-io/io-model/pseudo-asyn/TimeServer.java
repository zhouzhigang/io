import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Pseudo Asynchronous(Blocking) I/O server using ExecutorService.
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
                // improve: Handler with ExecutorService and Pool
                TimeServerHandlerExecutePool executor = new TimeServerHandlerExecutePool(50, 10000); // create I/O thread pool
            while (true) {
                // blocking on accept
                socket = server.accept();
                // improve: Using execute service execute the task
                executor.execute(new TimeServerHandler(socket));
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

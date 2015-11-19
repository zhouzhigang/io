# Socket

`java.net`

## Socket

A `socket` is an endpoint for communication.
The actual work of the socket is performed by an instance of the `SocketImpl` class.

    public class Socket implements java.io.Closeable
    public abstract class SocketImpl implements SocketOptions

Attributes:

    // The implementation of this Socket.
    SocketImpl impl;
    // The factory for all client sockets.
    private static SocketImplFactory factory = null;

Constructors:

There are 10 constructors(1 private, 1 protected, 2 @Deprecated), Most of them will call the private constructor.

    // Creates an unconnected socket
    public Socket() {
        setimpl();
    }

    // Creates a socket and connects it to the specified remote host on the specified remote port.
    public Socket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        this(address != null ? new InetSocketAddress(address, port) : null,
            new InetSocketAddress(localAddr, localPort), true);
    }

    private Socket(SocketAddress address, SocketAddress localAddr, boolean stream) throws IOException {
        setImpl();

        // backward compatibility
        if (address == null)
            throw new NullPointerException();
        try {
            createImpl(stream);
            if (localAddr != null)
                bind(localAddr);
            connect(address);
        } catch (IOException | IllegalArgumentException | SecurityException e) {
            try {
                close();
            } catch (IOException ce) {
                e.addSuppressed(ce);
            }
            throw e;
        }
    }


    void setImpl() {
        if (factory != null) {
            impl = factory.createSocketImpl();
            checkOldImpl();
        } else {
            // No need to do checkOldImpl() here, we know it's an up to date SocketImpl
            impl = new SocksSocketImpl();
        }
        if (impl != null) {
            impl.setSocket(this);
        }
    }

Another method to get `Socket` is by `ServerSocket.accept()`.

Methods:

    public void connect(SocketAddress endpoint, int timeout) throws IOException    
    public void bind(SocketAddress bindpoint) throws IOException
    public InputStream getInputStream() throws IOException
    public OutputStream getOutputStream() throws IOException


## ServerSocket


    public class ServerSocket implements java.io.Closeable

    private SocketImpl impl;

Constructors:

    // Creates an unbound server socket.
    public ServerSocket() throws IOException {
        setImpl();
    }

    // Create a server with the specified port, listen backlog, and local IP address to bind to.
    public ServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        setImpl();
        if (port < 0 || port > 0xFFFF)
            throw new IllegalArgumentException("Port value out of range: " + port);
        if (backlog < 1)
            backlog = 50; // requested maximum length of the queue of incoming connections
        try {
            bind(new InetSocketAddress(bindAddr, port), backlog);
        } catch(SecurityException e) {
            close();
            throw e;
        } catch(IOException e) {
            close();
            throw e;
        }
    }

    public ServerSocket(int port) throws IOException {
        this(port, 50, null);
    }

## DatagramSocket

    public class DatagramSocket implements java.io.Closeable

    DatagramSocketImpl impl;

## InetAddress

* `InetAddress`
* `Inet4Address`
* `Inet6Address`
* `InetSocketAddress`
* `InterfaceAddress`

## URLConnection

* `URLConnection`
* `HttpURLConnection`

## URI and URL

* `URI`: Uniform Resource Identifier, A URI can be further classified as a locator, a name, or both.
* `URL`: Uniform Resource Locator, a pointer to a "resource" on the World Wide Web.
* `URLClassLoader`

# I/O Stream

## InputSream and OutputStream

Declaration

    public abstract class InputStream implements Closeable
    public abstract class OutputStream implements Closeable, Flushable

`InputStream` `read()`

    // Reads the next byte of data from the input stream.
    public abstract int read() throws IOException;
    // Reads some number of bytes from the input stream and stores them into byte array b
    public int read(byte b[]) throws IOException {}
    // Reads up to len bytes of data from the input stream into an array of bytes
    public int read(byte b[], int off, int len) throws IOException {}

`OutputStream` `write()`

    // Writes the specified byte to this output stream.
    public abstract void write(int b) throws IOException;
    // Writes b.length bytes from the specified byte array to outputstream
    public void write(byte b[]) throws IOException {}
    // Write len  bytes from the specified byte array starting at offset to ouput stream
    public void write(byte b[], int off, int len) throws IOException {}

Subclass of `InputStream`

* `ByteArrayInputStream`
* `ObjectInputStream`
* `PipedInputStream`
* `SequenceInputStream`
* `StringBufferInputStream`
* `FileInputStream`
* `FilterInputStream`

Subclass of `OutputStream`

* `ByteArrayOutputStream`
* `ObjectOutputStream`
* `PipedOutputStream`
* `FileOutputStream`
* `FilterOutputStream`

## FileInputStream and FileOutputStream

Attributes

    private final FileDescriptor fd; // handle to the open file
    private final boolean append; // only in OutputStream, is for append
    private FileChannel channel = null; // support NIO channel
    private final Object closeLock = new Object(); // lock when close file
    private volatile boolean closed = false; // close tag
    private static final ThreadLocal<Boolean> runningFinalize = new ThreadLocal<>(); // is run finalize


Native methods - `open()`, `read()`, `write()`, `skip()`, `available()`

    private native void open(String name) throws FileNotFoundException;
    public native int read() throws IOException;
    private native int readBytes(byte b[], int off, int len) throws IOException;
    // Skips over and discards n bytes of data from input stream
    public native long skip(long n) throws IOException;
    // Returns an estimate of the number of remaining bytes that can be read or skip over
    public native int available() throws IOException;

    private native void open(String name, boolean append) throws FileNotFoundException;
    private native void write(int b, boolean append) throws IOException;
    private native void writeBytes(byte b[], int off, int len, boolean append) throws IOException;

Constructor

    public FileInputStream(File file) throws FileNotFoundException {
        String name = (file != null ? file.getPath() : null);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkRead(name);
        }
        if (name == null) {
            throw new NullPointerException();
        }
        fd = new FileDescriptor();
        fd.incrementAndGetUseCount();
        open(name);
    }

    public FileOutputStream(File file, boolean append) throws FileNotFoundException {
        // same with FileInputStream
        this.append = append; // the only difference
        // same with FileInputStream
    }


## ByteArrayInputStream and ByteArrayOutputStream

Attributes

    // InputStream - An array of bytes that was provided by the creator of the stream.
    protected byte buf[];

    // OutputStream - The buffer where data is stored.
    protected byte buf[];

Constructor

    public ByteArrayInputStream(byte buf[], int offset, int length) {
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.mark = offset;
    }

    // default size is 32: this(32)
    public ByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: " + size);
        }
        buf = new byte[size];
    }

## PipedInputStream and PipedOutputStream

A piped input stream should be connected to a piped output stream; the piped input stream then provides whatever data bytes are written to the piped output stream.
A piped output stream can be connected to a piped input stream to create a communications pipe.


## SequenceInputStream

Logical concatenation of other input streams.

Attributes

    Enumeration e;
    InputStream in;

Methods

    public int read() throws IOException {
        if (in == null) {
            return -1;
        }
        int c = in.read();
        if (c == -1) { // end of current stream
            nextStream(); // enumeration next strream
            return read();
        }
        return c;
    }


## StringBufferInputStream

`@Deprecated` allows an application to create an input stream in which the bytes read are supplied by the contents of a string.

## ObjectInputStream and ObjectOutputStream

Declaration

Also implements `ObjectInput` and `ObjectStreamConstant`

    public class ObjectInputStream
        extends InputStream implements ObjectInput, ObjectStreamConstants
    public class ObjectOutputStream
        extends OutputStream implements ObjectOutput, ObjectStreamConstants

Used to Serialize object.

## FilterInputStream and FilterOutputStream

`Decorator` extends `Component` and contains a `Component`

    public class FilterInputStream extends InputStream

    // The input stream to be filtered.
    protected volatile InputStream in;
    
    protected FilterInputStream(InputStream in) {
        this.in = in;
    }

Just a simple wrapper

    public int read() throws IOException {
        return in.read();
    }

## BufferedInputStream and BufferedOutputStream

`ConcreteDecorator` extends the `Decorator`

    public class BufferedInputStream extends FilterInputStream 
    public class BufferedOutputStream extends FilterOutputStream

    private static int defaultBufferSize = 8192;
    // The internal buffer array where the data is stored.
    protected volatile byte buf[];
    // Input - The index one greater than the index of the last valid byte in the buffer
    // Output - The number of valid bytes in the buffer.
    protected int count;
    // Input - current position, index of the next character to be read
    protected int pos;


    public synchronized int read() throws IOException {
        if (pos >= count) {
            fill(); // Fills the buffer with more data
            if (pos >= count)
                return -1;
        }
        return getBufIfOpen()[pos++] & 0xff;
    }

    public synchronized void write(int b) throws IOException {
        if (count >= buf.length) {
            flushBuffer(); // flush the internal buffer: out.write(buf, 0 count)
        }
        buf[count++] = (byte)b;
    }


## DataInputStream and DataOutputStream

Also implements `DataInput` or `DataOutput` interface

    public class DataInputStream extends FilterInputStream implements DataInput
    public class DataOutputStream extends FilterOutputStream implements DataOutput

Read or write Primary data type.

## PrintStream

`System.out`, System.error`

    public class PrintStream extends FilterOutputStream implements Appendable, Closeable

All kinds of `print()`, `println()`, `printf()`, `format()` methods

## PushbackInputStream

Can "push back" or "unread" one byte.

    // The pushback buffer.
    protected byte[] buf;
    // The position within the pushback buffer from which the next byte will be read
    // When the buffer is empty, pos = buf.length; when buffer is full, pos = 0
    protected int pos;

    public void unread(int b) throws IOException {
        ensureOpen();
        if (pos == 0) {
            throw new IOException("Push back buffer is full");
        }
        buf[--buf] = (byte)b;
    }




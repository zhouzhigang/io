# Character Streams

## Reader and Writer

Abstract class for reading/writing to character streams (since JDK 1.1).
`Readable` and `Appendable` etc. are used for NIO(since 1.5).

    public abstract class Reader implements Readable, Closeable
    public abstract class Writer implements Appendable, Closeable, Flushable


    abstract public int read(char cbuf[], int off, int len) throws IOException;
    abstract public void write(char cbuf[], int off, int len) throws IOException;

Subclass of `Reader` and `Writer`

* `CharArrayReader`, `CharArrayWriter` - `ByteArrayInputStream`, `ByteArrayOutputStream`
* `PipedReader`, `PipedWriter` - `PipedInputStream`, `PipedOutputStream`
* `StringReader`, `StringWriter` - `StringBufferInputStream`

* `FilterReader`, `FilterWriter` - `FilterInputStream`, `FilterOutputStream`
* `BufferedReader`, `BufferedWriter` - `BufferedInputStream`, `BufferedOutputStream`
* `LineNumberReader` - `LineNumberInputStream`
* `PushbackReader` - `PushbackInputStream`
* `PrintWriter` - `PrintStream`


## InputStreamReader and OutputStreamWriter

An InputStreamReader is a bridge from byte streams to character streams.
An OutputStreamWriter is a bridge from character streams to byte streams.

    public class InputStreamReader extends Reader
    public class OutputStreamWriter extends Writer


    private final StreamDecoder sd; // InputStreamReader
    private final StreamEncoder se; // OutputStreamWriter

    public InputStreamReader(InputStream in, String charsetName) throws UnsupportedEncodingException {
        super(in); // Reader: lock
        if (charsetName == null)
            throw new NullPointerException("charsetName");
        sd = StreamDecoder.forInputStreamReader(in, this, charsetName); // InputStreamReader
        // se = StreamEncoder.forOutputStreamWriter(out, this, charsetName); // OutputStreamWriter
    }

    public int read(char cbuf[], int offset, int length) throws IOException {
        return sd.read(cbuf, offset, length);
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        se.write(cbuf, off, len);
    }

### StreamDecoder and StreamEncoder(Not belong Java SE API)

    public class StreamDecoder extends Reader
    public class StreamEncoder extends Writer
    
    private Charset cs;
    
    private CharsetDecoder decoder;
    private InputStream in;

    private CharsetEncode encoder;
    private final OutputStream out;

### Adapter

Adapter: 
* Class Adapter
* Object Adapter


__Compare Different Wrapper Method__

* Adapter: Convert one interface to another
* Decorator: Improve function dynamically(keep origin interface)
* Proxy: Provides the same interface as the proxied-for class and typically does some housekeeping stuff on it own. Control the logic(AOP)
* Facade: Simple gateway to a complicated set of function


## FileReader and FileWriter

    public class FileReader extends InputStreamReader
    public class FileWriter extends OutputStreamWriter

    public FileReader(String fileName) throws FileNotFoundException {
        super(new FileInputStream(fileName)); // InputStreamReader(InputStream)
    }

    public FileWriter(String fileName, boolean append) throws IOException {
        super(new FileOutputStream(fileName, append)); // OutputStreamWriter(OutputStream)
    }

## File 

`File`: An abstract representation of file and directory pathnames.

    public class File implements Serializable, Comparable<File>

Attributes

    static private FileSystem fs = FileSystem.getFileSystem();
    private String path;
    private transient int prefixLength;
    public static final String separator = "" + separatorChar;
    public static final char pathSeparatorChar = fs.getPathSeparator();
    public static final String pathSeparator = "" + pathSeparatorChar;

Methods

* Constructors
* File Path
* File Attributes
* File
* Serialize
* NIO support

    public String[] list(FilenameFilter filter)
    public File[] listFiles(FilenameFilter filter)
    public File[] listFiles(FileFilter filter)

## FileFilter and FileNameFilter

    public interface FileFilter {
        boolean accept(File pathname);
    }

    public interface FilenameFilter {
        boolean accept(File dir, String name);
    }

# RandomAccessFile

    public class RandomAccessFile implements DataOutput, DataInput, Closeable


    private FileDescriptor fd;
    private FileChannel channel = null;
    private boolean rw;

    public native long getFilePointer() throws IOException;

## Reference
* [Reader and Writer](http://www.molotang.com/articles/780.html) [InputStreamReader and OutputStreamWriter](http://www.molotang.com/articles/782.html) [File and RandomAccessFile](http://www.molotang.com/articles/827.html)

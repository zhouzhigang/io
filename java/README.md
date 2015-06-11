# Java IO

## Brief History of Java IO

Java 1.4 NIO
Java 7 NIO.2
JSR-203

## Basic Concept
A `stream` is an abstraction that either produces or consumes information.
A stream is linked to a physical device by the I/O system.
All streams behave in the same manner, even if the actual physical devices they are linked to differ.

`Input stream`: only can read data from it, can't write data to it.
`Output Stream`: only can write data to it, can't read data from it.

There are two distinct I/O class hierarchies: one for bytes and one for characters.
`Byte stream`: 8 bits
`Character stream`: 16 bits

`Node Stream`: work with the IO device or file directly.
`Process Stream`: wrapper the node stream, make it more easy to use or more effective.

## IO Class Overview

It is large but easy to use because of well-thought-out design.
It contains classes that enable a byte array, a character array, or a string to be used as source or target of I/O operations.
It also provides the ability to set or obtain various attributes associated with a file, itself, such as its read/write status, whether the file is a directory, or if it is hidden.
You even obtain a list of files within a directory.

|                | Byte Input             | Byte Output            | Char Input         | Char Output         |
|----------------|------------------------|------------------------|--------------------|---------------------|
|Abstract        |`InputStream`           |`OutputStream`          |`Reader`            |`Writer`             |
|Access File     |`FileInputStream`       |`FileOutputStream`      |`FileReader`        |`FileWriter`         |
|Access Array    |`ByteArrayInputStream`  |`ByteArrayOutputStream` |`CharArrayReader`   |`CharArrayWriter`    |
|Access Pip      |`PipedInputStream`      |`PipedOutputStream`     |`PipedReader`       |`PipedWriter`        |
|Access String   |                        |                        |`StringReader`      |`StringWriter`       |
|Buffer          |`BufferedInputStream`   |`BufferedOutputStream`  |`BufferedReader`    |`BufferedWriter`     |
|Convert         |                        |                        |`InputStreamReader` |`OutputStreamWriter` |
|Object          |`ObjectInputStream`     |`ObjectOutputStream`    |`ObjectReader`      |`ObjectWriter`       |
|Filter          |`FilterInputStream`     |`FilterOutputStream`    |`FilterReader`      |`FilterWriter`       |
|Print           |                        |`PrintStream`           |                    |`PrintWriter`        |
|Pushback        |`PushbackInputStream`   |                        |`PushbackReader`    |                     |
|Data            |`DataInputStream`       |`DataOutputStream`      |                    |                     |

Some other special IO stream like `AudioInputStream`, `CipherInputStream`, `DeflaterInputStream`, `ZipInputStream` are not in `java.io` package.
`java.util.zip` create a compressed file, or decompress a file.

## Byte Stream
Byte streams are defined by two class hierarchies: one for input and one for output.
`InputStream` Abstract class that describes stream input
    `BufferedInputStream` Buffered input stream
    `DataInputStream` An input stream that contains methods for reading Java's standard data types
    `ObjectInputStream`
    `FileInputStream` Input stream that reads from a file
    `ByteArrayInputStream` Input stream that reads from a byte array
    `PushbackInputStream` Input stream that allows bytes to be returned to the stream.
    `PipedInputStream` Input pipe
    `FilterInputStream` Implements InputStream and allows the contents of another stream to be altered (filtered)

`OutputStream` Abstract class that describes stream output
    `BufferedOutputStream` Buffered output stream
    `DataOutputStream` An output stream that contains methods for writing Java's standard data types
    `ObjectOutputStream`
    `FileOutputStream` Output stream that writes to a file
    `ByteArrayOutputStream` Output stream that writes to a byte array
    `PipedOutputStream` Output pipe
    `FilterOutputStream` Implements OutputStream and allows the contents of another stream to be altered (filtered)
    `PrintStream` Output stream that contains print() and println()

## Character Stream
`Reader` Abstract class that describes character stream input.
    `InputStreamReader` Input stream that translates bytes to characters
    `BufferedReader` Buffered input character stream
    `StringReader` Input stream that reads from a string
    `CharArrayReader` Input stream that reads from a character array
    `FileReader` Input stream that reads from a file
    `FilterReader` Filtered reader
    `LineNumberReader` Input stream that counts lines
    `PipedReader` Input pipe
    `PushbackReader` Input stream that allows characters to be returned to the input stream.

`Writer` Abstract class that describes character stream output
    `OutputStreamWriter` Output stream that translates characters to bytes
    `BufferedWriter` Buffered output character stream
    `StringWriter` Output stream that writes to a string
    `CharArrayWriter` Output stream that writes to a character array
    `FileWriter` Output stream that writes to a file
    `FilterWriter` Filtered writer
    `PipedWriter` Output pipe
    `PrintWriter` Output stream that contains print() and println()

## Compressed File Streams
`DeflaterInputStream` Reads data, compressing the data in the process.
`DeflaterOutputStream` Writes data, compressing the data in the process.
`GZIPInputStream` Reads a GZIP file.
`GZIPOutputStream` Writes a GZIP file.
`InflaterInputStream` Reads data, decompressing the data in the process.
`InflaterOutputStream` Writes data, decompressing the data in the process.
`ZipInputStream` Reads a ZIP file.
`ZipOutputStream` Writes a ZIP file.

## The I/O Interfaces
`Closeable`
`DataInput`
`DataOutput`
`Externalizable`
`FileFilter`
`FilenameFilter`
`Flushable`
`ObjectInput`
`ObjectInputValidation`
`ObjectOutput`
`ObjectStreamConstants`
`Serializable`

## Examples
Read Bytes from a File
Write Bytes to a File
Buffer Byte-Based File I/O

Read Characters from a File
Write Characters to a File
Buffer Character-Based File I/O

Read and Write Random-Access Files

Obtain File Attributes
Set File Attributes
List a Directory

Compress and Decompress Data
Create a ZIP file
Decompress a ZIP file
Serialize Objects

## Reference
* [Java I/O, NIO, and NIO.2](http://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html)

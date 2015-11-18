# NIO

## NIO Overview

### Buffer

* `Buffer`
    + `capacity`: number of elements it contains.
    + `limit`: index of the first element that should not be read or written.
    + `position`: index of the next element to be read or written.
    + `mark`: index to which its position will be reset.
    + `clear()`: makes a buffer ready for a new sequence of channel-read or relative put operation.
    + `flip()`:  makes a buffer ready for a new sequence of channel-write or relative get operation.
    + `rewind()`: makes a buffer ready for re-reading the data that it already contains.

    mark <= position <= limit <= capacity

`ByteBuffer`, `CharBuffer`, `DoubleBuffer`, `FloatBuffer`, `IntBuffer`, `LongBuffer`, `ShortBuffer`

`MappedByteBuffer`

### Channel

`Channel`: Full-duplex

* `SelectableChannel`
* `FileChannel`

### Selector

A multiplexor of `SelectableChannel` objects.

A selectable channel's registration with a selector is represented by a `SelectionKey` object. 


## NIO Server

    NIOServer                       Reactor Thread                      IOHandler
      | -1.Open ServerSocketChannel---> |
      | -2.Binding InetSocketAddress--> |
      |                                 | -3.Create Selector,Start thread-> |
      | -4.Register to Selector-------> |
      |                                 | -5.Selector loop for key--------> |
      |                                 | <-6.handleAccept()--------------- |
                                        |                                   | 7. Set param for client socket
                                        | <-8.Register SelectionKey.OP_READ-|
                                        | <-9.handleRead() to ByteBuffer--- |
                                        |                                   | 10. decode message
                                        | <-11.Write ByteBuffer to Channel- |


    // 1. Open ServerSocketChannel, listening for client event
    ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
    // 2. Binding port, and set non-blocking
    acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));
    acceptorSvr.configureBlocking(false);
    // 3. Create Reactor thread; Create multipleor selector; start the thread
    Selector selector = Selector.open();
    new Thread(new ReactorTask()).start();
    // 4. Register ServerSocketChannel to Sekector of the Reactor thread, listening for ACCEPT event
    SelectionKey key = acceptorSvr.register(selector, SelectionKey.OP_ACCEPT, ioHandler);
    // 5. polling on ready keys 
    int num = selector.select();
    Set selectedKeys = selector.selectedKeys();
    Iterator it = selectedKeys.iterator();
    while (it.hasNext()) {
        SelectionKey key = (SelectionKey)it.next();
        // deal with I/O event
        // ..
    }
    // 6. Handle new client connection
    SocketChannel channel = svrChannel.accept();
    // 7. Set non-blocking for client
    channel.configureBlocking(false);
    channel.socket().setReuseAddress(true);
    // 8. Register new connection to selector of Reactor thread, listening for read operation
    SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ, ioHandler);
    // 9. Read request message from client asynchronously
    int readNumber = channel.read(receiveBuffer);
    // 10. Decode for ByteBuffer, 
    while (buffer.hasRemain()) {
        byteBuffer.mark();
        Object message = decode(byteBuffer);
        if (message == null) {
            byteBuffer.reset();
            break;
        }
        messageList.add(message);
    }
    if (!byteBuffer.hasRemain()) {
        byteBuffer.clear();
    } else {
        byteBuffer,compact();
    }
    if (messageList != null & !messageList.isEmpty()) {
        for (Object messageE : messageList) {
            handlerTask(messageE);
        }
    }
    // 11. Encode POJO object to ByteBuffer, call ScoketChannel.write(), write message to clinet asynchronously
    socketChannel.write(buffer)

## NIO Client

    NIOClient                       Reactor Thread                      IOHandler
      | -1.Open SocketChannel---------> |
      | -2.Set NonBlock and TCP param-> |
      | -3.Connect to Server Asyn-----> |
      | -4.Goto 10 if connected succ--> |
      | -5.Register OP_CONNECT event--> |
      |                                 | -6.Create Selector,Start thread-> |
      |                                 | -7.Selector looping on keys-----> |
      |                                 | -8.handleConnected()------------> |
                                        |                                   | 9.Check if connected, goto 10 if yes
                                        | <-10.Register OP_READ------------ |
                                        | <-11.handleRead() to ByteBuffer-- |
                                        |                                   | 12. decode message
                                        | <-13.Write ByteBuffer to Channel- |


    // 1. Open SocketChannel, binding local address
    SocketChannel clientChannel = SocketChannel.open();
    // 2. Set non-blocking for SocketChannel, and Set TCP parameters
    clientChannel.configureBlocking(false);
    socket.setReuseAddress(true);
    socket.setReceiveBufferSize(BUFFER_SIZE);
    socket.setSendBufferSize(BUFFER_SIZE);
    // 3. Connect to server asynchronously
    boolean connected = clientChannel.connect(new InetSocketAddress("ip", port));
    // 4. Check if connected success or not. If success, register OP_READ to selector directly, otherwise register OP_CONNECT
    if (connected) {
        clientChannel.register(selector, SelectionKey.OP_READ, ioHandler);
    } else {
        clientChannel.register(selector, SelectionKey.OP_CONNECT, ioHandler);
    }
    // 5. Register OP_CONNECT to Selector of Reactor thread, listening to TCP ACK response from server
    clientChannel.register(selector, SelectionKey.OP_CONNECT, ioHandler);
    // 6. Create Reactor thread; Create Selector; Start thread
    Selector selector = Selector.open();
    new Thread(new ReactorTask()).start();
    // 7. looping for ready keys
    int num = selector.select();
    Set selectedKeys = selector.selectedKeys();
    Iterator it = selectedKeys.iterator();
    while (it.hasNext()) {
        SelectionKey key = (SelectionKey) it.next();
        // deal with I/O event
    }
    // 8. Handle connect event
    if (key.isConnectable()) {
        // handlerConnect();
    }
    // 9. Check connect result, register to Selector if connected successfully
    if (channel.finishConnect()) {
        registerRead();
    }
    // 10. register OP_READ event to selector
    clientChannel.register(selector, SelectionKey.OP_READ, ioHandler);
    // 11. Read request message asynchronously
    int readNumber = channel.read(receiveBuffer);
    // 12. Decode for ByteBuffer, 
    while (buffer.hasRemain()) {
        byteBuffer.mark();
        Object message = decode(byteBuffer);
        if (message == null) {
            byteBuffer.reset();
            break;
        }
        messageList.add(message);
    }
    if (!byteBuffer.hasRemain()) {
        byteBuffer.clear();
    } else {
        byteBuffer,compact();
    }
    if (messageList != null & !messageList.isEmpty()) {
        for (Object messageE : messageList) {
            handlerTask(messageE);
        }
    }
    // 13. Encode POJO object to ByteBuffer, call ScoketChannel.write(), write message to clinet asynchronously
    socketChannel.write(buffer)

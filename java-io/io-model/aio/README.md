# Aynchronous I/O

NIO 2.0

* `java.util.concurrent.Future` async result
* `java.nio.channels` as async param

`CompletionHandleri<V, A>` interface

    public void completed(V result, A attachment);
    public failed(Throwable exc, A attchment);

# Linux Network IO Model

Every thing is file in Linux. It will return a __file descriptor__(`fd`, which is a number pointer to a structure. For socket, it called __socketfd__) when call a system call to read or write file.

5 kinds of I/O models

## Synchronous Blocking I/O Model

The system call does not return until the datagram arrives and is copied into our application buffer, or an error occurs.

    App                 Kernel
    recvfrom    ->      no datagram ready
                            |       (wait)
                            |       (wait)
                        datagram ready
                        copy datagram
    process     <-      copy completed

Note: use UDP for this example instead of TCP because with UDP, the concept of data being "ready" to read is simple: either an entire datagram has been received or it has not. With TCP it gets more complicated, as additional variables such as the socket's low-water mark come into play.

__Pros__: Easy to use and well understood; Ubiquitous.
__Cons__: Does not maximize I/O throughput; Causes all threads in a process to block if that process uses green threads.

## Synchronous Non-blocking I/O Model

when an I/O operation that I request cannot be completed without putting the process to sleep, do not put the process to sleep, but return an error instead.
__polling__: a loop calling `recvfrom` on a nonblocking descriptor.
This is often a waste of CPU time, but this model is occasionally encountered.

    App                 Kernel
    recvfrom    ->      no datagram ready
           <-EWOULDBLOCK-   |
    recvfrom    ->      no datagram ready
           <-EWOULDBLOCK-   |
    recvfrom    ->      datagram ready
                        copy data
    process     <-      copy completed

__Pros__:

* If no I/O is available other work can be completed in the meantime
* When I/O is available, is does not block the thread (even models with green threads)

__Cons__:

* Does not maximize I/O throughput for the application
* Lots of system call overhead – constantly making system calls to see if I/O is ready
* Can be high latency if I/O arrives and a system call is not made for a while

## I/O Multiplexing Model

Call `select` or `poll` and block in one of these two system calls, instead of blocking in the actual I/O system call.

We block in a call to `select`, waiting for the datagram socket to be readable.

    App                 Kernel
    select      ->      no datagram ready
                             |
         <-return readable-  |
    recvfrom    ->      datagram ready
                        copy data
    process     <-      copy completed

__Pros__:

* When I/O is available is does not block
* Lots of I/O can be issued to execute in parallel
* Notifications occur when one or more file descriptors are ready (helps to improve I/O throughput)

__Cons__:

* Calling `select()`, `poll()`, or `epoll_wait()` blocks the calling thread (entire application if using green threads)
* Lots of file descriptors for I/O means lots that have to be checked (can be avoided with `epoll`)

## Signal driven I/O model

Telling the kernel to notify us with the `SIGIO` signal when the descriptor is ready.

    App                         Kernel
    establish SIGIO     ->      no datagram ready
    signal handler      <-
                                      |
    signal handle  <-deliver SIGIO-   |
    recvfrom            ->      datagram ready
                                copy data
    process             <-      copy completed


## Asynchronous I/O model
Telling the kernel to start the operation and to notify us when the entire operation (including the copy of the data from the kernel to our buffer) is complete.

__Callback__: a callback function is called when the I/O has completed

Difference:
signal-driven I/O: the kernel tells us when an I/O operation can be __initiated__.
asynchronous I/O: the kernel tells us when an I/O operation is __complete__.

    
    App                         Kernel
    aio_read            ->      no datagram ready
                        <-            |
                                      |
                                datagram ready
                                copy data
    process <-deliver singanl-  copy completed


__Pros__:

* Helps maximize I/O throughput by allowing lots of I/O to issued in parallel
* Allows application to continue processing while I/O is executing, callback or POSIX signal when done

__Cons__:

* Wrapper for libaio may not exist for your programming environment
* Network I/O may not be supported

## Reference
* [UNIX network programming - 6.2 I/O Models](http://www.masterraghu.com/subjects/np/introduction/unix_network_programming_v1.3/ch06lev1sec2.html)
* [I/O models: how you move your data matters](http://timetobleed.com/io-models-how-you-move-your-data-matters/)
* [Construction of high-performance web site - I/O Model](http://www.cricode.com/3680.html)

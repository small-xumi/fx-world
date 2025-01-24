package hut.natsufumij.central;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AIOServer {
    private static volatile AsynchronousSocketChannel channel;
    private static final int port = 8080;

    public static void bootServer() throws IOException {
       Thread server =  new Thread(() -> {
            AsynchronousServerSocketChannel serverChannel = null;
            try {
                serverChannel = AsynchronousServerSocketChannel.open();
                serverChannel.bind(new InetSocketAddress(port));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Server started on port 8080");


            AsynchronousServerSocketChannel finalServerChannel = serverChannel;
            serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Void attachment) {
                    channel = result;
                    finalServerChannel.accept(null, this); // Accept the next connection

                    ByteBuffer buffer = ByteBuffer.allocate(1024*100);//100KB
                    result.read(buffer, buffer, new CompletionHandler<>() {
                        @Override
                        public void completed(Integer bytesRead, ByteBuffer buffer) {
                            buffer.flip();
                            //1024*100 = 102400 [六位数]
                            String message = new String(buffer.array(), 0, bytesRead);
                            handleMsg(message, channel);
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer buffer) {
                            exc.printStackTrace();
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    exc.printStackTrace();
                }
            });

            // Keep the server running
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        server.start();
    }

    public static void handleMsg(String message, AsynchronousSocketChannel channel) {
        System.out.println("Received message: " + message);

        String res = "Echo";
        if(message.startsWith("AppInfo")){
            //[cmd]central,console,ok,no
            res = "[cmd]central,console,ok,no";
        }
        // Echo the message back to the client
        channel.write(ByteBuffer.wrap((res).getBytes()));
    }
}

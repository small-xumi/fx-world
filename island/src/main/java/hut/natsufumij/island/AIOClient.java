package hut.natsufumij.island;

import hut.natsufumij.island.app.AppEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AIOClient {
    public static volatile AsynchronousSocketChannel channel;
    private static HelloApplication app;

    public static void bootClient(HelloApplication ap) {
        app=ap;
        Thread client = new Thread(() -> {
            try {
                AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open();
                InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8080);
                Future<Void> future = clientChannel.connect(hostAddress);
                future.get(); // Wait until the connection is established

                System.out.println("Connected to server");

                String appData = app.pushAppData().toString();

                // Send a message to the server
                String message = appData;
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                Future<Integer> writeResult = clientChannel.write(buffer);
                writeResult.get(); // Wait until the message is sent

                ByteBuffer read = ByteBuffer.allocate(1024*100);//100KB
                // Loop to continuously read messages from the server
                while (true) {
                    read.clear();
                    Future<Integer> readResult = clientChannel.read(read);
                    int bytesRead = readResult.get(); // Wait until the response is received

                    if (bytesRead == -1) {
                        break; // Server has closed the connection
                    }

                    read.flip();
                    byte[] bytes = new byte[read.remaining()];
                    read.get(bytes);
                    String response = new String(bytes);
                    //[cmd]central,console,ok,no
                    if(response.startsWith("[cmd]")){
                        //
                        String cmd = response.substring(5);
                        String[] cmds = cmd.split(",");
                        String[] args;
                        if(cmds.length>3){
                            args = new String[cmds.length-3];
                            System.arraycopy(cmds,3,args,0,args.length);
                        }else {
                            args = new String[]{};
                        }
                        ap.receiveEvent(new AppEvent(cmds[0],cmds[1],cmds[2],args));
                    }
                    System.out.println("Received response: " + response);
                }

                clientChannel.close();
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        client.start();
    }

    public static void send(String message){
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        Future<Integer> writeResult = channel.write(buffer);
    }
}

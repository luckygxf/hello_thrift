package com.gxf.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by guanxianseng on 2017/8/18.
 */
public class Client {
    private static SocketChannel client = null;

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init() throws IOException {
        client  = SocketChannel.open();
        client.connect(new InetSocketAddress("127.0.0.1", 8888));
    }

    private static void sendMessage(String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes());
        buffer.flip();
        while(buffer.hasRemaining()){
            client.write(buffer);
        }
//        client.close();
    }

    private static void readMessage(){
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                try {
                    client.read(buffer);
//                    buffer.flip();
                    System.out.println(new String(buffer.array()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        reader.start();
    }

    public static void main(String[] args) throws IOException {
        sendMessage("hello nio");
//        readMessage();
    }
}

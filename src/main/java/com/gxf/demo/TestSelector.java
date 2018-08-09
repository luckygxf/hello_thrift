package com.gxf.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TestSelector {
    private static Selector selector;

    public static void main(String[] args) throws IOException, InterruptedException {
        startSelectorThread();
        Thread.sleep(2000);
        selector.wakeup();
    }

    private static void startSelectorThread(){
        Runnable selectorTask = () -> {
            try{
                String host = "127.0.0.1";
                int port = 8888;
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                SocketAddress socketAddress = new InetSocketAddress(host, port);
                serverSocketChannel.bind(socketAddress);
                selector = Selector.open();
                while(true){
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    System.out.println("selector start select .....");
                    Set<SelectionKey> selectionKeySet =  selector.selectedKeys();
                    for(Iterator<SelectionKey> iterator = selectionKeySet.iterator(); iterator.hasNext(); ){
                        SelectionKey selectionKey = iterator.next();
                        System.out.println(selectionKey.toString());
                        selectionKeySet.remove(selectionKey);
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel clientChannel = serverSocketChannel1.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        iterator.remove();
                    }

                    System.out.println("selector end select ....");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }; //task
        Thread thread = new Thread(selectorTask);
        thread.start();
    }
}

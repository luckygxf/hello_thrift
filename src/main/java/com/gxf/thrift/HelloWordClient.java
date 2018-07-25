package com.gxf.thrift;

import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.IOException;

public class HelloWordClient {
    public static void main(String[] args) throws Exception {
        main_asy();
    }

    public static void main_syn() throws Exception{
        TTransport transport = new TSocket("127.0.0.1", 7912);
        TProtocol protocol = new TBinaryProtocol(transport);

        // 创建client
        com.gxf.thrift.HelloWordService.Client client = new com.gxf.thrift.HelloWordService.Client(protocol);

        transport.open();  // 建立连接

        // 第一种请求类型
        com.gxf.thrift.Request request = new com.gxf.thrift.Request()
                .setType(com.gxf.thrift.RequestType.SAY_HELLO).setName("winwill2012").setAge(24);
        System.out.println(client.doAction(request));

        // 第二种请求类型
        request.setType(com.gxf.thrift.RequestType.QUERY_TIME).setName("winwill2012");
        System.out.println(client.doAction(request));

        transport.close();  // 请求结束，断开连接
    }

    public static void main_asy() throws Exception {
        String address = "127.0.0.1";
        int port = 7912;
        int clientTimeout = 300;

        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(address, port, clientTimeout);
            TProtocolFactory protocol = new TCompactProtocol.Factory();
            HelloWordService.AsyncClient asyncClient = new HelloWordService.AsyncClient(protocol, clientManager, transport);
            System.out.println("Client calls .....");
            MyCallback callBack = new MyCallback();
            com.gxf.thrift.Request request = new com.gxf.thrift.Request()
                    .setType(com.gxf.thrift.RequestType.SAY_HELLO).setName("winwill2012").setAge(24);

            asyncClient.doAction(request, callBack);
            System.out.println("finish client call");

            while (true) {
                Thread.sleep(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

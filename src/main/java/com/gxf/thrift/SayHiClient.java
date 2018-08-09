package com.gxf.thrift;

import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.IOException;

public class SayHiClient {
    public static void main(String[] args) throws Exception {
        main_asy();
    }

    public static void main_syn() throws Exception{
        TTransport transport = new TSocket("127.0.0.1", 7912);
        TProtocol protocol = new TBinaryProtocol(transport);

        // 创建client
        SayHiService.Client client = new SayHiService.Client(protocol);

        transport.open();  // 建立连接

        // 第一种请求类型
        Request request = new Request()
                .setType(RequestType.SAY_HELLO).setName("guanxiangfei").setAge(24);
        System.out.println(client.sayHi(request));

        // 第二种请求类型
        request.setType(RequestType.QUERY_TIME).setName("guanxiangfei");
        System.out.println(client.sayHi(request));

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
            SayHiService.AsyncClient asyncClient = new SayHiService.AsyncClient(protocol, clientManager, transport);
            System.out.println("Client calls .....");
            MyCallback callBack = new MyCallback();
            Request request = new Request()
                    .setType(RequestType.SAY_HELLO).setName("guanxiangfei").setAge(24);

            asyncClient.sayHi(request, callBack);
            System.out.println("finish client call");

            while (true) {
                Thread.sleep(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

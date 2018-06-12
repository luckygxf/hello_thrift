package com.gxf.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class HelloWordClient {
    public static void main(String[] args) throws Exception {
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

}

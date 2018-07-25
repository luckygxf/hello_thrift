package com.gxf.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;

import java.net.ServerSocket;

public class HelloWordServer {

    public static void main(String[] args) throws Exception {
        int port = 7912;

        //异步调用
        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);
        HelloWordService.Processor processor = new HelloWordService.Processor(new HelloWordServiceImpl());
        THsHaServer.Args arg = new THsHaServer.Args(socket);
        // 高效率的、密集的二进制编码格式进行数据传输
        // 使用非阻塞方式，按块的大小进行传输，类似于 Java 中的 NIO
        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));
        TServer server = new THsHaServer(arg);
        server.serve();
        System.out.println("#服务启动-使用:非阻塞&高效二进制编码");

        //同步调用
//        ServerSocket socket = new ServerSocket(7912);
//        TServerSocket serverTransport = new TServerSocket(socket);
//        com.gxf.thrift.HelloWordService.Processor processor = new com.gxf.thrift.HelloWordService.Processor(
//                new HelloWordServiceImpl());
//        TServer server = new TSimpleServer(processor, serverTransport);
//        System.out.println("Running server...");
//        server.serve();
//        TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();
//        TSimpleJSONProtocol.Factory factory = new TSimpleJSONProtocol.Factory();

        /**
         * 关联处理器与GreetingService服务实现
         */
//        TProcessor processor = new HelloWordService.Processor(new HelloWordServiceImpl());

//        TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
//        serverArgs.processor(processor);
//        serverArgs.protocolFactory(proFactory);
//        TServer server = new TThreadPoolServer(serverArgs);
//        TServer server1 = new TSimpleServer(serverArgs);
        System.out.println("Start server on port 7912...");

//        server1.serve();
    }

}

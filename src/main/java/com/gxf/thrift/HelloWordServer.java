package com.gxf.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import java.net.ServerSocket;

public class HelloWordServer {

    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(7912);
        TServerSocket serverTransport = new TServerSocket(socket);
//        com.gxf.thrift.HelloWordService.Processor processor = new com.gxf.thrift.HelloWordService.Processor(
//                new HelloWordServiceImpl());
//        TServer server = new TSimpleServer(processor, serverTransport);
//        System.out.println("Running server...");
//        server.serve();
        TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();

        /**
         * 关联处理器与GreetingService服务实现
         */
        TProcessor processor = new HelloWordService.Processor(new HelloWordServiceImpl());

        TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
        serverArgs.processor(processor);
        serverArgs.protocolFactory(proFactory);
        TServer server = new TThreadPoolServer(serverArgs);
        System.out.println("Start server on port 7912...");

        server.serve();
    }

}

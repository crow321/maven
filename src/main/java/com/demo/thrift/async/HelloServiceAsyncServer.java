package com.demo.thrift.async;

import com.demo.thrift.Hello;
import com.demo.thrift.ipml.HelloServiceIpml;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TNonblockingServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * HelloServiceAsyncServer 通过 java.nio.channels.ServerSocketChannel 创建非阻塞的服务器端等待客户端的连接。
 * Created by zhangp on 2017/4/20.
 */
public class HelloServiceAsyncServer {
    /**
     * 启动Thrift异步服务器
     * @param args
     */
    public static void main(String[] args) {
        //传输层使用非阻塞方式
        TNonblockingServerTransport serverTransport;
        try {

            serverTransport = new TNonblockingServerSocket(10002);//可能会抛出异常
            Hello.Processor processor = new Hello.Processor(new HelloServiceIpml());
            TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();
            Args targs =new Args(serverTransport);
            targs.processor(processor);
            targs.protocolFactory(proFactory);
            //多线程服务器端使用非阻塞式
            TServer server = new TNonblockingServer(targs);
            System.out.println("start asyncServer on port 10002...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}

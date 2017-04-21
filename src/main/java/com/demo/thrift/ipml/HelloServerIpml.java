package com.demo.thrift.ipml;

import com.demo.thrift.Hello;
import com.demo.thrift.HelloServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 模拟HelloServiceServer的服务端
 * Created by zhangp on 2017/4/20.
 */
@Service("helloServer")
public class HelloServerIpml implements HelloServer {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);
    /**
     *  自动注解时无法注入，值始终为0？？？
     *  原因：使用注解时对象不能通过new方式产生，按照spring思想，应该通过getBean()方法获取对象。
     */
    @Value("${socket.port}")
    private int port;

    @Override
    public void startServer() {
        try {
            //设置服务器端口8901
            TServerSocket serverTransport = new TServerSocket(port);
            //设置协议工厂TBinaryProtocol.Factory()
            TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();
            //关联处理器与Hello服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceIpml());
            //设置Tserver服务器
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
            tArgs.processor(processor);
            tArgs.protocolFactory(proFactory);
            TServer server = new TThreadPoolServer(tArgs);

            logger.info("Start server on port " + port);
            //server进入阻塞监听状态，直到客户端发送请求消息
            server.serve();
        } catch (TTransportException e) {
            logger.error("Start server fail：服务器绑定端口被占用");
            e.printStackTrace();
        }
    }
    /**
     * 启动Thrift服务器
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        HelloServer server = (HelloServer) context.getBean("helloServer");
        server.startServer();

    }

}

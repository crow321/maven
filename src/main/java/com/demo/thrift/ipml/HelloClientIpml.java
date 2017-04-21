package com.demo.thrift.ipml;

import com.demo.thrift.Hello;
import com.demo.thrift.HelloClient;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by zhangp on 2017/4/20.
 */
@Service("helloClient")
public class HelloClientIpml implements HelloClient{

    private static final Logger logger = LoggerFactory.getLogger(HelloClientIpml.class);
    @Value("${socket.port}")
    private int port ;

    public void startClient() {
        try {
            //设置调用的服务地址为本地，端口为8900
            TTransport transport = new TSocket("localhost", port);
            transport.open();//TTE
            //设置传输协议 TBinaryProtcol
            TProtocol protocol = new TBinaryProtocol(transport);

            Hello.Client client = new Hello.Client(protocol);
            logger.info("client calling on port " + transport);
            //调用服务端的服务1
            client.helloVoid();
            //调用服务端的服务2
            int n = client.helloInt(100);
            //调用服务端的服务3
            String s = client.helloString("服务器会答复我么...");
            //调用服务端的服务4
            boolean b = client.booleanHello(false);
            /**
             * 在Thrift中，直接调用一个返回 null 值的方法会抛出 TApplicationException 异常。
             * 我们要捕获该异常，并进行相应的处理。
             */
            //调用服务端的服务5
            String s1 =  client.helloNull();
            System.out.println("n = " + n + "\ns = " + s + "\nb = " + b + "s1 = " +s1);
            transport.close();
        } catch (TTransportException e) {
            logger.error("TTransportException: 与服务器连接失败，服务器可能未启动");
            e.printStackTrace();
        } catch (TException e) {
            /**
             * 处理Null值的异常
             */
            if ((e instanceof TApplicationException)
                    && (((TApplicationException) e).getType() == TApplicationException.MISSING_RESULT)) {
                logger.error("The result of helloNull function is NULL");
            }
        }
    }

    /**
     * 模拟客户端的启动，并请求服务
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        HelloClient client = (HelloClient) context.getBean("helloClient");
        client.startClient();
    }
}

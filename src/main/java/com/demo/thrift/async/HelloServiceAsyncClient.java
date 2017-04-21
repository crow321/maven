package com.demo.thrift.async;

import com.demo.thrift.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TTransport;

import java.io.IOException;

/**
 * 创建异步客户端实现代码
 * 调用 Hello.AsyncClient 访问服务端的逻辑实现，将 MethodCallback 对象作为参数传入调用方法中.
 * Created by zhangp on 2017/4/20.
 */
public class HelloServiceAsyncClient {
    /**
     * 调用Hello服务
     * @param args
     */
    public static void main(String[] args) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();//IOE
            TNonblockingTransport transport = new TNonblockingSocket("localhost", 10002);
            Factory protocol = new TBinaryProtocol.Factory();
            Hello.AsyncClient asyncClient = new Hello.AsyncClient(protocol, clientManager, transport);

            System.out.println("client calling...");

            MethodCallback callback = new MethodCallback();
            asyncClient.helloString("Hello Thrift!", callback);//TE
            /**
             * 通过以下的循环代码实现了同步效果
             */
            Object res = callback.getResponse();
            while (res == null) {
                res = callback.getResponse();
            }
            System.out.println(((Hello.AsyncClient.helloString_call)res).getResult());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}

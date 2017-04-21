package com.demo.thrift.ipml;


import com.demo.thrift.Hello;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangp on 2017/4/20.
 */
public class HelloServiceIpml implements Hello.Iface{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceIpml.class);

    @Override
    public String helloString(String para) throws TException {
        System.out.println("客户端调用了helloString方法");
        return para;
    }

    @Override
    public int helloInt(int para) throws TException {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            logger.error("helloInt被捕获异常了...");
            e.printStackTrace();
        }
        System.out.println("客户端调用了helloInt方法");
        return para;
    }

    @Override
    public boolean booleanHello(boolean para) throws TException {
        System.out.println("客户端调用了helloBoolean方法");
        return false;
    }

    @Override
    public void helloVoid() throws TException {
        System.out.println("客户端调用了helloVoid方法");
        System.out.println("Hello World!");
    }

    @Override
    public String helloNull() throws TException {
        System.out.println("客户端调用了hellonull方法");
        return null;
    }
}

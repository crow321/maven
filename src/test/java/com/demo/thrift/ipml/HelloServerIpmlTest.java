package com.demo.thrift.ipml;

import com.demo.thrift.HelloServer;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloServerIpmlTest extends TestCase {
    @Autowired
    private HelloServer server;
    @Test
    public void test(){
        server.startServer();
    }
}
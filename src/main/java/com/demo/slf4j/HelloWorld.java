package com.demo.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 练习使用slf4j日志
 * Created by zhangp on 2017/4/20.
 */
public class HelloWorld {
    private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        logger.info("Hello World...");

        int status = 0;
        if (status == 0) {
            logger.info("status:{}", status);
        } else {
            logger.info("status:{}", status);
        }
        logger.info("end!");
        logger.debug("this is a debug log!");
        logger.error("this is an error message!");
    }
}

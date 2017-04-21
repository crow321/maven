package com.demo.thrift.async;

import org.apache.thrift.async.AsyncMethodCallback;

import java.util.Objects;

/**
 * Created by zhangp on 2017/4/20.
 */
public class MethodCallback implements AsyncMethodCallback{
    private Object response = null;

    /**
     * onComplete 方法接收服务处理后的结果
     * @param response
     */
    @Override
    public void onComplete(Object response) {
        //处理服务返回的结果
        this.response = response;
    }

    /**
     * onError 方法接收服务处理过程中抛出的异常
     * @param exception
     */
    @Override
    public void onError(Exception exception) {

    }

    public Object getResponse() {
        return response;
    }
}

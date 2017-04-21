package com.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * Created by crow on 2017/4/12.
 */
public class TimeNetyServer {

    private int port;
    public void startServer(int port) {
        //1.创建两个线程组，一个用于处理accept事件，一个用于处理accept到的事件的线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();//处理accept事件的线程池
        EventLoopGroup workGroup = new NioEventLoopGroup();//处理accept到的事件的线程池

        try {
            //创建NIO服务器的辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置事件循环对象，前者处理accept事件，后者处理accept到的所有事件
            bootstrap.group(bossGroup, workGroup);
                //使用该类对象 建立新的accept事件的channel，用于构造serversocketchannel的工厂类
            bootstrap.channel(NioServerSocketChannel.class);
            /*  BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
                用于临时存放已完成三次握手的请求的队列的最大长度
            **/
            bootstrap.option(ChannelOption.SO_BACKLOG, 100);
            //为accept channel的pipeline预添加的inboundhandler
            bootstrap.childHandler(new ChildChannelHandler());

            //通过bind()方法创建ChannelFuture对象,并与端口绑定，sync：同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务器监听端口关闭
            future.channel().close().sync();

        }
        catch (Exception e) {
            System.out.println("startServer异常： " + e);
        }
        finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }

    }

    public static void main(String[] args) {

    }


}

package com.example.nettydemo.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-24 12:09
 */
public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        Attribute<Object> serverName = ch.attr(AttributeKey.valueOf("clientKey"));
                        System.out.println("服务端: " + serverName + " childHandler");
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) {
                Attribute<Object> serverName = ch.attr(AttributeKey.valueOf("serverName"));
                System.out.println("服务端: " + serverName + " 启动中 handler");
            }
        });

        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");
        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // ChannelOption.SO_KEEPALIVE 表示是否开启TCP底层心跳机制，true为开启
        // ChannelOption.TCP_NODELAY 表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true);
        bind(serverBootstrap, 1000);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }

}
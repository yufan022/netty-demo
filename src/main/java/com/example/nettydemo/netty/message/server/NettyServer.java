package com.example.nettydemo.netty.message.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 11:11
 */
public class NettyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);

        serverBootstrap.channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                System.out.println("服务端： 接收请求");
                                nioSocketChannel.pipeline().addLast(new ServerHandler());
                            }
                        }
                );
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel nioSocketChannel) throws Exception {
                System.out.println("服务端: 启动中 handler");
            }
        });
        bind(serverBootstrap, 1000);
    }

    private static void bind(ServerBootstrap serverBootstrap, Integer port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.out.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });

    }
}
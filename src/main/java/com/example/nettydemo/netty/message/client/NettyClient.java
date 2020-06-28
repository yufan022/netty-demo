package com.example.nettydemo.netty.message.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 11:11
 */
public class NettyClient {
    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                System.out.println("客户端启动");
                nioSocketChannel.pipeline().addLast(new ClientHandler());
            }
        });
        bootstrap.connect("localhost", 1000).addListener(future -> {
            if (future.isSuccess()) {
            } else {
                System.out.println("失败");
            }
        });

    }
}

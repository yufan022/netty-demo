package com.example.nettydemo.netty.demo3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-24 17:19
 */
public class NettyClient {
    private static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        Attribute<Object> clientName = ch.attr(AttributeKey.valueOf("clientName"));
                        System.out.println("客户端: " + clientName + " handler");

                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");
        // ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
        // ChannelOption.SO_KEEPALIVE 表示是否开启 TCP 底层心跳机制，true 为开启
        // ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启
        bootstrap
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
        // 4.建立连接
        connect(bootstrap, "localhost", 1000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.err.println("连接失败，开始重连");
                connect(bootstrap, host, port);
            }
        });
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }
}
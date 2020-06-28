package com.example.nettydemo.netty.message.client;

import com.example.nettydemo.netty.message.protocol.PacketCodeC;
import com.example.nettydemo.netty.message.protocol.request.LoginRequestPacket;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 19:10
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端发送消息");
        //        ByteBuf buffer = ctx.alloc().buffer();
        //        buffer.writeBytes("Hello Netty!".getBytes());
        //        ctx.channel().pipeline().writeAndFlush(buffer);

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId("1");
        loginRequestPacket.setUsername("wyf");
        loginRequestPacket.setPassword("123456");

        ByteBuf encode = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
        ctx.channel().pipeline().writeAndFlush(encode);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + "客户端接收消息");
        ByteBuf byteBuf = (ByteBuf) msg;
        String s = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println(s);
    }
}

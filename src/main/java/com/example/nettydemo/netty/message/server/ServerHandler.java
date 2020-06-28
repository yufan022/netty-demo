package com.example.nettydemo.netty.message.server;

import com.example.nettydemo.netty.message.protocol.Packet;
import com.example.nettydemo.netty.message.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 19:11
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + "服务端接收消息");
//        ByteBuf buff = (ByteBuf) msg;
//        System.out.println(buff.toString(Charset.forName("utf-8")));

        ByteBuf msg1 = (ByteBuf) msg;
        Packet decode = PacketCodeC.INSTANCE.decode(msg1);
        System.out.println(decode);
        //        ByteBuf byteBuf = getByteBuf(ctx);
//        ctx.channel().writeAndFlush(byteBuf);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        System.out.println(new Date() + "服务端回发消息");
        byte[] bytes = "Hi Netty!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        return buffer.writeBytes(bytes);
    }
}

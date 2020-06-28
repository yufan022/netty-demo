package com.example.nettydemo.netty.message.protocol;

import com.example.nettydemo.netty.message.protocol.command.Command;
import com.example.nettydemo.netty.message.protocol.request.LoginRequestPacket;
import com.example.nettydemo.netty.message.serialize.Serializer;
import com.example.nettydemo.netty.message.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 19:48
 */
public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x123456;
    public static final PacketCodeC INSTANCE = new PacketCodeC();
    private final Map<Byte, Class<? extends Packet>> packetMap = new HashMap<>();
    private final Map<Byte, Serializer> serializerMap = new HashMap<>();

    private PacketCodeC() {
        packetMap.put(Command.LOGIN_REQUEST.value, LoginRequestPacket.class);
        JSONSerializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializerAlogrithm(), jsonSerializer);
    }

    public ByteBuf encode(ByteBufAllocator bba, Packet packet) {
        // 序列化对象
        byte[] serializer = Serializer.DEFAULT.serializer(packet);
        // byteBuf对象
        ByteBuf byteBuf = bba.ioBuffer();
        // 编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(serializer.length);
        byteBuf.writeBytes(serializer);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 解码
        // 魔数
        int magicNumber = byteBuf.readInt();
        // 协议版本
        byte version = byteBuf.readByte();
        // 序列化算法
        byte serializerAlogrithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestPacket = getRequestPacket(command);
        Serializer serializer = getSerializer(serializerAlogrithm);
        // 反序列化
        return serializer.deserialize(bytes, requestPacket);

    }

    private Class<? extends Packet> getRequestPacket(byte command) {
        return packetMap.get(command);
    }

    private Serializer getSerializer(byte serializerAlogrithm) {
        return serializerMap.get(serializerAlogrithm);
    }
}

package com.example.nettydemo.netty.message.serialize;

import com.alibaba.fastjson.JSON;
import com.example.nettydemo.netty.message.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 20:13
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    byte[] serializer(Object object);

    <T> T deserialize(byte[] bytes, Class<T> clazz);

    byte getSerializerAlogrithm();
}

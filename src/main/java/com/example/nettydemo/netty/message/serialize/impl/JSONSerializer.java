package com.example.nettydemo.netty.message.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.example.nettydemo.netty.message.serialize.Serializer;
import com.example.nettydemo.netty.message.serialize.SerializerAlogrithm;
import io.netty.buffer.ByteBuf;
import lombok.val;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-31 10:53
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte[] serializer(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON.value;
    }
}

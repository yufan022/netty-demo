package com.example.nettydemo.netty.message.serialize;

public enum SerializerAlogrithm {

    JSON((byte) 1);

    public byte value;

    SerializerAlogrithm(byte value) {
        this.value = value;
    }
}

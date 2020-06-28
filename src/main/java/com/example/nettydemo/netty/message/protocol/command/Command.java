package com.example.nettydemo.netty.message.protocol.command;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 19:49
 */
public enum Command {
    LOGIN_REQUEST((byte) 1),
    LOGIN_RESPONSE((byte) 2),
    MESSAGE_REQUEST((byte) 3),
    MESSAGE_RESPONSE((byte) 4);

    public byte value;

    Command(byte value) {
        this.value = value;
    }
}

package com.example.nettydemo.netty.message.protocol;

import lombok.Data;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 19:44
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 获取指令
     * @return
     */
    public abstract Byte getCommand();
}

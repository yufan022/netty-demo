package com.example.nettydemo.netty.message.protocol.request;

import com.example.nettydemo.netty.message.protocol.Packet;
import com.example.nettydemo.netty.message.protocol.command.Command;
import lombok.Data;

/**
 * @Description:
 * @Author: Wyufan
 * @create: 2019-01-30 19:51
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST.value;
    }
}

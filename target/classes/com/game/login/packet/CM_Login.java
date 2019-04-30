package com.game.login.packet;

import org.msgpack.annotation.Message;

//登录协议
@Message
public class CM_Login {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

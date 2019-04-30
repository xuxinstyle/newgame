package com.netty.packet;

import org.msgpack.annotation.Message;

@Message
public class CM_Test1 {
    private int code;
    private String str;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

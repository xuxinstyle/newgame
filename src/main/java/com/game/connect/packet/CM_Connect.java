package com.game.connect.packet;

import org.msgpack.annotation.Message;
/**
 * @Author：xuxin
 * @Date: 2019/6/17 12:30
 */
public class CM_Connect {

    private String context;


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

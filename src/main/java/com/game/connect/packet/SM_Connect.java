package com.game.connect.packet;

import org.msgpack.annotation.Message;
/**
 * 服务端的向客户端返回连接消息
 */

public class SM_Connect {

    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}

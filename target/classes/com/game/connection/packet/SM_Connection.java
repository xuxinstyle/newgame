package com.game.connection.packet;

import org.msgpack.annotation.Message;
//服务端的向客户端返回连接消息
@Message
public class SM_Connection {
    private static final int pid = 2;
    private String hello;

    public int getPid() {
        return pid;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}

package com.socket.core;

import org.msgpack.annotation.Message;

@Message
public class MyPack {
    //协议Id
    private Integer pId;
    //自己的协议包
    private byte[] packet;
    //发送时间
    private long time;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

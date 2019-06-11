package com.socket.core;

import org.msgpack.annotation.Message;

import java.util.Arrays;

@Message
public class MyPack {
    //协议Id
    private Integer pId;
    //自己的协议包
    private byte[] packet;

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

    @Override
    public String toString() {
        return "MyPack{" +
                "pId=" + pId +
                ", packet=" + Arrays.toString(packet) +
                '}';
    }
}

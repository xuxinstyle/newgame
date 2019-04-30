package com.socket.core;

import org.msgpack.annotation.Message;

@Message
public class MyPack {
    //协议Id
    private Integer pId;
    //自己的协议包
    private Object cm;
    //发送时间
    private long time;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Object getCm() {
        return cm;
    }

    public void setCm(Object cm) {
        this.cm = cm;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

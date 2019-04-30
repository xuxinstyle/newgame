package netty.core;

import org.msgpack.annotation.Message;

import java.io.Serializable;


public class CMessagePack implements Serializable {
    //协议ID
    private int msgIndex;
    //数据对象
    private byte[] date;
    //发送时间
    private long time;
    public int getMsgIndex() {
        return msgIndex;
    }

    public void setMsgIndex(int msgIndex) {
        this.msgIndex = msgIndex;
    }

    public byte[] getDate() {
        return date;
    }

    public void setDate(byte[] date) {
        this.date = date;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CMessagePack{" +
                "msgIndex=" + msgIndex +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}

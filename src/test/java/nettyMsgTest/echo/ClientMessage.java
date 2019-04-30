package nettyMsgTest.echo;

public class ClientMessage {
    //协议ID
    private final int msgIndex;
    //数据对象
    private final Object date;
    //数据长度
    private final int length;
    //发送时间
    private final long time;
    public ClientMessage(int msgIndex, Object date, int length, long time){
        this.msgIndex = msgIndex;
        this.date = date;
        this.length = length;
        this.time = time;
    }
    public int getMsgIndex() {
        return msgIndex;
    }

    public Object getDate() {
        return date;
    }

    public int getLength() {
        return length;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CMessagePack{" +
                "msgIndex=" + msgIndex +
                ", date=" + date +
                ", length=" + length +
                ", time=" + time +
                '}';
    }
}

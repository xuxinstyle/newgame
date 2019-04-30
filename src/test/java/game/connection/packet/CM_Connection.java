package game.connection.packet;

import org.msgpack.annotation.Message;

//连接服务器时客户端发生的协议
@Message
public class CM_Connection {
    private static final int id = 1;
    private String context;
    public static int getId() {
        return id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

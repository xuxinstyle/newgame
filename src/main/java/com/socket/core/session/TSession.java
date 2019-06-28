package com.socket.core.session;

import com.socket.utils.JsonUtils;
import com.socket.core.MyPack;
import com.socket.dispatcher.core.RegistSerializerMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.util.Map;


/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:44
 */
public class TSession {
    private static final Logger logger = Logger.getLogger(TSession.class);
    private String accountId ;
    private final long createTime = System.currentTimeMillis();
    private final Channel channel;
    private final String ip;
    private String inetIp;
    private final String port;
    private int mapId;
    public TSession(Channel channel){
        this.channel = channel;
        if(channel != null){
            this.ip = channel.remoteAddress().toString();
        }else{
            this.ip = "127.0.0.1:8888";
        }
        String[] adds = ip.split(":");
        this.inetIp = adds[0].substring(1);
        this.port = adds[1];

    }
    public void sendPacket(Object res) {
        try{
            if(res==null){
                MyPack pack = new MyPack();
                channel.writeAndFlush(pack);
                return;
            }
            int opIndex = 0;
            for (Map.Entry<Integer, Class<?>> entry :RegistSerializerMessage.ID_CLASS_MAP.entrySet()) {
                if(entry.getValue().equals(res.getClass())){
                    opIndex = entry.getKey();
                    break;
                }
            }
            MyPack pack = new MyPack();
            pack.setpId(opIndex);
            pack.setPacket(JsonUtils.object2Bytes(res));
            channel.writeAndFlush(pack);
        }catch (Exception e){
            String msg = String.format("encode %s error.",res != null ? res.getClass().getSimpleName():"null");
            logger.error(msg,e);
        }
    }
    public void logout(String accountId){

        channel.close();
    }
    public boolean isActive(){
        return channel.isActive();
    }
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}

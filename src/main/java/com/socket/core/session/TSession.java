package com.socket.core.session;

import com.socket.Utils.ProtoStuffUtil;
import com.socket.core.MyPack;
import com.socket.dispatcher.action.IActionDispatcher;
import com.socket.dispatcher.config.RegistSerializerMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:44
 */
public class TSession {
    private static final Logger logger = Logger.getLogger(TSession.class);
    // 里面放在线的玩家账号信息 <信息标识，玩家的accountId>
    private ConcurrentHashMap<String, String> attribute = new ConcurrentHashMap<>();
    private static int index = 0;
    private final int id = ++index;
    private final long createTime = System.currentTimeMillis();
    private final Channel channel;
    private final String ip;
    private String inetIp;
    private final String port;
    //private final ITaskQueue taskQueue;
    private final IActionDispatcher actionDispatcher;
    public TSession (Channel channel){
        this(channel, null);
    }
    //FIXME:项目中有一个IParse参数，次数没了解其用处，暂时不加
    public TSession(Channel channel, IActionDispatcher actionDispatcher){
        this.channel = channel;
        this.actionDispatcher = actionDispatcher;
        if(channel != null){
            this.ip = channel.remoteAddress().toString();
        }else{
            this.ip = "127.0.0.1:8888";
        }
        String[] adds = ip.split(":");
        this.inetIp = adds[0].substring(1);//FIXME:这里不太理解为什么
        this.port = adds[1];
        //this.taskQueue = new ConcurrentId
    }
    public void sendPacket(Object res) {
        try{
            int opIndex = 0;
            for (Map.Entry<Integer, Class<?>> entry :RegistSerializerMessage.idClassMap.entrySet()) {
                if(entry.getValue().equals(res.getClass())){
                    opIndex = entry.getKey();
                }
            }
            if(opIndex == 0){
                logger.error("发送协议错误，没有对应的协议id");
                return;
            }
            long start = System.nanoTime();
            MyPack pack = new MyPack();
            pack.setTime(start);
            pack.setpId(opIndex);
            pack.setPacket(ProtoStuffUtil.serializer(res));
            channel.writeAndFlush(pack);
        }catch (Exception e){
            String msg = String.format("encode %s error.",res != null ? res.getClass().getSimpleName():"null");
            logger.error(msg,e);
        }
    }



    public void setAttribute(String key, String value) {
        attribute.put(key, value);
    }
}

package com.netty.dispatcher.core;

import com.netty.core.TSession;
import com.netty.dispatcher.action.ActionDispatcherAdapter;
import com.netty.dispatcher.executor.IIdentifyThreadPool;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:50
 */
public class ActionDispatcher extends ActionDispatcherAdapter {
    private static Logger logger = Logger.getLogger(ActionDispatcher.class);
    private Map<Class<?>, IHandlerInvoke> handlerMap = new HashMap<>();

    //线程池
    private final IIdentifyThreadPool executor;

    public ActionDispatcher(IIdentifyThreadPool executor) {
        this.executor = executor;
    }

    @Override
    public void handle(TSession session, int opIndex, Object packet, long decodeTime) {
        //doHandle(session, opIndex, packet,decodeTime);
        executor.addSessionTask(session ,new IoHandleEvent(this, session, opIndex, packet, decodeTime));
    }
    public void doHandle(TSession session, int opIndex, Object packet, long decodeTime) {
        System.out.println("到达dohandle");
        IHandlerInvoke defintion = handlerMap.get(packet.getClass());
        System.out.println("defintion="+defintion);
        if(defintion == null){
            throw  new NullPointerException("no any handlerDefintion found for packet :"
            + packet.getClass().getSimpleName());
        }
        Object res = defintion.invoke(session, opIndex, packet);
        if(res != null){
            session.sendPacket(opIndex, res);
        }
    }

    public void registHandlerDefintion(Class<?> clz, IHandlerInvoke invoke) {
        IHandlerInvoke pre = handlerMap.put(clz, invoke);
        if(pre != null){
            throw new IllegalArgumentException("too much PolicyDefintion fro packet : "
            + clz.getSimpleName());
        }
    }
    public static final class IoHandleEvent implements Runnable{

        private final TSession session;
        private final Object packet;

        private final ActionDispatcher dispatcher;
        private final int opIndex;
        private final long decodeTime;
        IoHandleEvent(ActionDispatcher dispatcher, TSession session, int opIndex, Object packet,
                      long decodeTime){
            this.dispatcher =dispatcher;
            this.decodeTime = decodeTime;
            this.session = session;
            this.opIndex = opIndex;
            this.packet = packet;
        }
        @Override
        public void run() {
            try {
                dispatcher.doHandle(session, opIndex, packet, decodeTime);
            }catch (Exception e){
                logger.error("消息[" + packet.getClass() + "[处理异常" , e);
            }
        }

        public TSession getSession() {
            return session;
        }

        public Object getPacket() {
            return packet;
        }


        public ActionDispatcher getDispatcher() {
            return dispatcher;
        }

        public int getOpIndex() {
            return opIndex;
        }

        public long getDecodeTime() {
            return decodeTime;
        }
    }


}


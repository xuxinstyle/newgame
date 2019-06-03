package com.socket.dispatcher.core;

import com.socket.Utils.ProtoStuffUtil;
import com.socket.core.session.TSession;
import com.socket.dispatcher.action.ActionDispatcherAdapter;
import com.socket.dispatcher.anno.HandlerAnno;
import com.socket.dispatcher.config.RegistSerializerMessage;
import com.socket.dispatcher.executor.IIdentifyThreadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:50
 */
@Component
public class ActionDispatcher extends ActionDispatcherAdapter implements BeanPostProcessor {
    private static Logger logger = LoggerFactory.getLogger(ActionDispatcher.class);
    private static Map<Class<?>, IHandlerInvoke> handlerMap = new HashMap<>();

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
        Class<?> aClass = RegistSerializerMessage.idClassMap.get(opIndex);
        if(aClass==null){
            return;
        }
        Object pack = ProtoStuffUtil.deserializer((byte[]) packet, aClass);
        if(logger.isDebugEnabled()){
            logger.debug("到达dohandle:pack="+pack.getClass());
        }

        IHandlerInvoke defintion = handlerMap.get(pack.getClass());
        if(logger.isDebugEnabled()){
            logger.debug("defintion="+defintion+" packet class:"+pack.getClass());
        }
        if(defintion == null){
            throw  new NullPointerException("no any handlerDefintion found for packet :"
            + packet.getClass().getSimpleName());
        }
        Object res = defintion.invoke(session, opIndex, pack);
        if(res != null){
            session.sendPacket( res);
        }

    }

    public void registHandlerDefintion(Class<?> clz, IHandlerInvoke invoke) {
        IHandlerInvoke pre = handlerMap.put(clz, invoke);

        /*System.out.println("put到handleMap中："+clz.getSimpleName()+" invoke:"+invoke.toString());
        System.out.println("get handleMap :"+handlerMap);*/
        if(pre != null){
            throw new IllegalArgumentException("too much PolicyDefintion fro packet : "
            + clz.getSimpleName());
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("bean:"+bean+beanName);
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(HandlerAnno.class)){
                HandlerDefintion def = HandlerDefintion.valueOf(bean, method);
                registHandlerDefintion(def.getClz(), def);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
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


package com.socket.dispatcher.core;

import com.game.SpringContext;
import com.game.base.executor.account.command.MessageCommand;
import com.game.connect.packet.CM_Connect;
import com.game.login.packet.CM_Login;
import com.game.register.packet.CM_Register;
import com.socket.utils.JsonUtils;
import com.socket.core.session.TSession;
import com.socket.dispatcher.action.ActionDispatcherAdapter;
import com.socket.dispatcher.anno.HandlerAnno;
import com.socket.dispatcher.config.RegistSerializerMessage;

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


    @Override
    public void handle(TSession session, int opIndex, Object packet) {
        Class<?> aClass = RegistSerializerMessage.ID_CLASS_MAP.get(opIndex);
        if(aClass==null){
            return;
        }
        Object pack = JsonUtils.bytes2Object((byte[]) packet, aClass);
        if(pack instanceof CM_Login){
            CM_Login cm = (CM_Login) pack;
            MessageCommand messageCommond = new MessageCommand(session, opIndex, pack,cm.getUsername());
            SpringContext.getAccountExecutorService().submit(messageCommond);

        } else if(pack instanceof CM_Register){
            CM_Register cm = (CM_Register) pack;
            MessageCommand messageCommond = new MessageCommand(session, opIndex, pack,cm.getUsername());
            SpringContext.getAccountExecutorService().submit(messageCommond);
        } else if(pack instanceof CM_Connect){
            doHandle(session,opIndex,pack);
        }else{
            if(session.getAccountId()!=null){
                MessageCommand messageCommond = new MessageCommand(session, opIndex, pack, session.getAccountId());
                SpringContext.getAccountExecutorService().submit(messageCommond);
            }
        }
    }

    public static void doHandle(TSession session, int opIndex, Object packet) {

        if(logger.isDebugEnabled()){
            logger.debug("到达dohandle:pack="+packet.getClass());
        }

        IHandlerInvoke defintion = handlerMap.get(packet.getClass());
        if(logger.isDebugEnabled()){
            logger.debug("defintion="+defintion+" packet class:"+packet.getClass());
        }
        if(defintion == null){
            throw  new NullPointerException("no any handlerDefintion found for packet :"
            + packet.getClass().getSimpleName());
        }
        Object res = defintion.invoke(session, opIndex, packet);
        if(res != null){
            session.sendPacket(res);
        }
    }

    public void registHandlerDefintion(Class<?> clz, IHandlerInvoke invoke) {
        IHandlerInvoke pre = handlerMap.put(clz, invoke);
        if(pre != null){
            throw new IllegalArgumentException("too much PolicyDefintion fro packet : "
            + clz.getSimpleName());
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

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

}


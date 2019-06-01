package com.socket.dispatcher.core;

import com.socket.core.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 *
 * @Author：xuxin
 * @Date: 2019/4/29 17:51
 */
public class HandlerDefintion implements IHandlerInvoke{

    private Logger logger = LoggerFactory.getLogger(HandlerDefintion.class);
    private final Object bean;
    private final Method method;
    /**
     * 类对象
     */
    private final Class<?> clz;
    /**
     * 索引类型
     */
    private final INDEXTYPE indextype;
    public HandlerDefintion(Object bean, Method method, Class<?> clz, INDEXTYPE indexType){
        this.bean = bean;
        this.method = method;
        this.clz = clz;
        this.indextype = indexType;
    }
    public Class<?> getClz(){
        return clz;
    }
    @Override
    public Object invoke(TSession session, int opIndex, Object packet) {
        if(logger.isDebugEnabled()) {
            logger.debug("session:" + session + " opIndex:" + opIndex + " packet:" + packet);
            logger.debug("method:" + method + " bean:" + bean);
        }
        ReflectionUtils.makeAccessible(method);
        Object result = null;
        switch (indextype){
            case INDEX:
                result = ReflectionUtils.invokeMethod(method, bean, session, opIndex, packet);
            case NOINDEX:
                result = ReflectionUtils.invokeMethod(method, bean, session, packet);
        }
        return result;
    }
    public static HandlerDefintion valueOf(Object bean, Method method){
        Class<?> clz = null;
        Class<?>[] clzs = method.getParameterTypes();
        if(clzs.length != 2 && clzs.length != 3){
            throw new IllegalArgumentException("class"+ bean.getClass().getSimpleName() + "method"
            + method.getName() + "this first parameter must be one [TSession] type parameter Exception");
        }
        if(!TSession.class.isAssignableFrom(clzs[0])){
            throw new IllegalArgumentException("class"+ bean.getClass().getSimpleName() + "method"
                    + method.getName() + "this first parameter must be one [TSession] type parameter Exception");
        }
        clz = clzs[1];
        INDEXTYPE type = INDEXTYPE.NOINDEX;
        if(clzs.length ==3){
            clz = clzs[2];
            type = INDEXTYPE.INDEX;
        }
        return new HandlerDefintion(bean,method, clz, type);
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public INDEXTYPE getIndextype() {
        return indextype;
    }

    @Override
    public String toString(){
        return String.format("[packet : %s][class : %s][method : %s]", clz.getSimpleName(),bean.getClass()
                .getSimpleName(), method.getName());
    }
    public enum INDEXTYPE{
        INDEX,NOINDEX;
    }
}

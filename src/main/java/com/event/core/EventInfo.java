package com.event.core;

import com.event.event.IEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 16:49
 */
public class EventInfo {
    private final Object bean;
    private final Method method;
    private final Class<? extends IEvent> clz;

    public EventInfo(Object bean, Method method, Class<? extends IEvent> clz){
        this.bean = bean;
        this.method = method;
        this.clz = clz;
    }
    public void invoke(IEvent event){
        /**
         * 使method变为可访问的
         */
        ReflectionUtils.makeAccessible(method);
        ReflectionUtils.invokeMethod(method, bean, event);
    }
}

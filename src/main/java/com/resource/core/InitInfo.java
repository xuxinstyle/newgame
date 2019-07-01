package com.resource.core;

import java.lang.reflect.Method;

/**
 * @Author：xuxin
 * @Date: 2019/7/1 22:50
 */
public class InitInfo {

    private final Class<?> clz;

    private final Object bean;

    private final Method method;

    public InitInfo(Class<?> clz, Object bean, Method method){
        this.clz = clz;
        this.bean = bean;
        this.method = method;
    }

    public Class<?> getClz() {
        return clz;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }
}

package com.netty.dispatcher.config;

import com.netty.dispatcher.anno.HandlerAnno;
import com.netty.dispatcher.core.ActionDispatcher;
import com.netty.dispatcher.core.EnhanceUtil;
import com.netty.dispatcher.core.HandlerDefintion;
import com.netty.dispatcher.core.IHandlerInvoke;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 17:37
 */
public class DispatcherHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

    private ApplicationContext applicationContext;

    private final String dispatcherBeanName;

    public DispatcherHandlerBeanPostProcessor(String dispatcherBeanName) {
        this.dispatcherBeanName = dispatcherBeanName;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("注册协议");
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(HandlerAnno.class)){
                HandlerDefintion def = HandlerDefintion.valueOf(bean, method);
                try{
                    IHandlerInvoke invoke = EnhanceUtil.createHandlerDefintion(def);
                    getActionDispatcher().registHandlerDefintion(def.getClz(), invoke);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    private ActionDispatcher getActionDispatcher() {
        return (ActionDispatcher) applicationContext.getBean(dispatcherBeanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

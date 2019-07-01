package com.resource.core;

import com.resource.anno.Init;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/1 22:32
 */
@Component
public class InitManager implements BeanPostProcessor {
    /**
     * 执行指定class下的指定方法
     * <beanName,List<Method>>
     */
    private static Map<Class<?> ,List<InitInfo>> classMethodsMap = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(InitManager.class);

    public void init(){
        for(List<InitInfo> initInfos:classMethodsMap.values()){
            for(InitInfo initInfo:initInfos){
                Method method = initInfo.getMethod();
                try {
                    method.invoke(initInfo.getBean());
                } catch (IllegalAccessException e) {
                    logger.error("类[{}],中的方法[{}]运行错误",initInfo.getClz().getSimpleName(),method.getName());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    logger.error("类[{}],中的方法[{}]运行错误",initInfo.getClz().getSimpleName(),method.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field:fields){
            if(field.isAnnotationPresent(Init.class)){
                Init init = field.getAnnotation(Init.class);
                String methodName = init.value();
                try {
                    Method method = clz.getMethod(methodName);
                    List<InitInfo> initInfos = classMethodsMap.get(clz);
                    if(initInfos==null){
                        initInfos = new ArrayList<>();
                        classMethodsMap.put(clz,initInfos);
                    }
                    initInfos.add(new InitInfo(clz,bean,method));

                } catch (NoSuchMethodException e) {
                    logger.error("类[{}]中的方法[{}]不存在",clz.getSimpleName(),methodName);
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}

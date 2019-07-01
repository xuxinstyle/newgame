package com.event.core;

import com.event.anno.EventAnn;
import com.event.event.IEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 16:40
 */
@Component
public class EvenManager {
    private static Map<Class<? extends IEvent> ,List<EventInfo>> receiverEventMap = new HashMap<>();

    private static final Logger logger  = LoggerFactory.getLogger(EvenManager.class);
    private ExecutorService[]  executors;
    private static final int EXCUOTRS_SIZE = 2;
    public void init(){
        executors = new ExecutorService[EXCUOTRS_SIZE];
        for (int i = 0;i<EXCUOTRS_SIZE;i++){
            executors[i] = Executors.newSingleThreadExecutor();
        }

    }
    public void registerEvent(Object bean){
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for(Method method :methods){
            if(method.isAnnotationPresent(EventAnn.class)){
                Class<?>[] clzs = method.getParameterTypes();
                if(clzs.length!=1){
                    logger.error("类{}中方法{}参数个数不为1",clz,method);
                    return;
                }
                if(!IEvent.class.isAssignableFrom(clzs[0])){
                    logger.error("类{}没有继承IEvent",clzs[0]);
                    return;
                }
                Class<? extends IEvent> eventClz = (Class<? extends IEvent>) clzs[0];
                EventInfo eventInfo = new EventInfo(bean, method, eventClz);
                registerEvent(eventClz, eventInfo);
            }
        }
    }

    private void registerEvent(Class<? extends IEvent> eventClz, EventInfo eventInfo) {
        if(!receiverEventMap.containsKey(eventClz)){
            receiverEventMap.put(eventClz, new CopyOnWriteArrayList<>());
        }
        if(!receiverEventMap.get(eventClz).contains(eventInfo)){
            receiverEventMap.get(eventClz).add(eventInfo);
        }else {
            logger.error("receiverDefintionMap中已经存在{}", eventInfo.toString());
        }
    }

    /**
     * 暂时只实现了同步，还没有实现异步
     * @param event
     */
    public void syncSubmit(IEvent event){
        doSubmitEvent(event);
    }

    private void doSubmitEvent(IEvent event) {
        List<EventInfo> eventInfos = receiverEventMap.get(event.getClass());
        if(eventInfos ==null|| eventInfos.isEmpty()){
            logger.error("receiverDefintionMap中没有相应的defintion");
        }
        for( EventInfo defintion: eventInfos){
            if(defintion!=null) {
                defintion.invoke(event);
            }
        }
    }
}

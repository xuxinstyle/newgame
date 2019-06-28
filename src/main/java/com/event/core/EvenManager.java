package com.event.core;

import com.event.anno.ReceiveAnn;
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
    private static Map<Class<? extends IEvent> ,List<ReceiverDefintion>> receiverDefintionMap = new HashMap<>();

    private static final Logger logger  = LoggerFactory.getLogger(EvenManager.class);
    private ExecutorService[]  executors;
    public void init(){
        
    }
    public void registerReceiver(Object bean){
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for(Method method :methods){
            if(method.isAnnotationPresent(ReceiveAnn.class)){
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
                ReceiverDefintion receiverDefintion = new ReceiverDefintion(bean, method, eventClz);
                registerDefintion(eventClz,receiverDefintion);
            }
        }
    }

    private void registerDefintion(Class<? extends IEvent> eventClz, ReceiverDefintion receiverDefintion) {
        if(!receiverDefintionMap.containsKey(eventClz)){
            receiverDefintionMap.put(eventClz, new CopyOnWriteArrayList<>());
        }
        if(!receiverDefintionMap.get(eventClz).contains(receiverDefintion)){
            receiverDefintionMap.get(eventClz).add(receiverDefintion);
        }else {
            logger.error("receiverDefintionMap中已经存在{}",receiverDefintion.toString());
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
        List<ReceiverDefintion> receiverDefintions = receiverDefintionMap.get(event.getClass());
        if(receiverDefintions==null||receiverDefintions.isEmpty()){
            logger.error("receiverDefintionMap中没有相应的defintion");
        }
        for( ReceiverDefintion defintion:receiverDefintions){
            defintion.invoke(event);
        }
    }
}

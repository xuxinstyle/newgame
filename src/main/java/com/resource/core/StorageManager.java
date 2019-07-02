package com.resource.core;

import com.resource.Storage;
import com.resource.StorageData;
import com.resource.anno.LoadResource;
import com.resource.model.ResourceDefinition;
import com.resource.reader.CsvRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 10:30
 */
@Component
public class StorageManager implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);
    /**
     * 静态类容器
     */
    private Map<Class<?>, ResourceDefinition> definitionMap = new ConcurrentHashMap<>();
    /**
     * 静态资源存储容器
     */
    private Map<Class<?>, Storage<?,?>> storageMap = new ConcurrentHashMap<>();
    /**
     * csv读取的缓存流容器
     */
    private Map<String, InputStream> caches = new ConcurrentHashMap<>();

    @Autowired
    private CsvRead csvRead;

    public void init(){
        for(Class<?> clz:definitionMap.keySet()) {
            ResourceDefinition def = definitionMap.get(clz);
            csvRead.readCsv(def, caches);
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clz = bean.getClass();
            if(clz.isAnnotationPresent(LoadResource.class)){
                ResourceDefinition def = ResourceDefinition.valueOf(bean);
                registResourceDefintion(def.getClz(), def);
                registResourceCacah(def.getLocation());
            }
            return bean;
        } catch (Exception e) {
            logger.error("注册资源[{}]失败",bean.getClass().getSimpleName());
            e.printStackTrace();
        }
        return bean;
    }


    public Collection<?> getResourceAll(Class<?> clz){
        Collection<?> values = storageMap.get(clz).getData().getValues().values();
        if(values==null){
            logger.error("获取资源对象失败！");
            return null;
        }
        return values;
    }

    public Storage<?, ?> getStorage(Class clz){
        return storageMap.get(clz);
    }


    /** 根据主键和clz获取Resource对象*/
    public <T> T getResource(Object key, Class<T> clz){
        Storage<?, ?> storage = getStorage(clz);
        T t = (T) storage.getData().getValues().get(key);
        if(t==null||!t.getClass().equals(clz)){
            /*logger.warn("获取{}资源{}失败！",key,clz.getSimpleName());*/
            return null;
        }
        return t;
    }

    /**
     * 将资源放进资源容器中
     * @param def
     * @param resourceMap
     */
    public void putStorage(ResourceDefinition def, Map<Object,Object> resourceMap) {
        Class<?> clz = def.getClz();
        /** <主键， Object>*/
        StorageData<Object,Object> data = new StorageData<>();
        data.setValues(resourceMap);
        Storage<Object,Object> storage = new Storage<>();
        storage.setData(data);

        storageMap.put(clz, storage);
    }

    /**
     * 获取到文件流并放入Cacah中
     * @param loaction
     */
    private void registResourceCacah(String loaction) {
        try {
            File file = new File(loaction);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            caches.put(loaction,inputStream);
        }catch (IOException e){
            logger.error("注册资源[{}]缓存失败",loaction,e);
        }
    }
    private void registResourceDefintion(Class<?> clz, ResourceDefinition def) {
        if(!definitionMap.containsKey(clz)) {
            definitionMap.put(clz, def);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

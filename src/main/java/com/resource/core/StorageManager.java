package com.resource.core;

import com.game.scence.resource.MapResource;
import com.resource.Storage;
import com.resource.StorageData;
import com.resource.anno.Resource;
import com.resource.other.ResourceDefinition;
import com.resource.reader.ReadXlsx;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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
    private static Map<Class<?>, Storage<?,?>> storageMap = new ConcurrentHashMap<>();
    /**
     * csv读取的缓存流容器
     */
    private static Map<String, InputStream> caches = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clz = bean.getClass();
        if(clz.isAnnotationPresent(Resource.class)){
            ResourceDefinition def = ResourceDefinition.valueOf(bean);
            registResourceDefintion(def.getClz(), def);
            registResourceCacah(def.getLocation());
            ReadXlsx readXlsx = new ReadXlsx();
            readXlsx.readXlsx(def,caches);
        }
        if(logger.isDebugEnabled()){
            logger.debug(definitionMap.toString());
            logger.debug(caches.toString());
        }
        return bean;
    }

    private void addStorageMap() {

    }

    public static Object getResource( Class<?> clz, Object key){
        if(logger.isDebugEnabled()){
            logger.debug(storageMap.get(MapResource.class).getData().getValues().toString());
        }
        Object object = storageMap.get(clz).getData().getValues().get(key);
        if(object==null||!object.getClass().equals(clz)){
            logger.error("获取资源对象失败！");
            return null;
        }
        return object;
    }
    public static Collection<?> getResourceAll(Class<?> clz){
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
            logger.error("获取资源对象失败！");
            return null;
        }
        return t;
    }
    public static void putStorage(ResourceDefinition def, Map<Object,Object> map) {
        Class<?> clz = def.getClz();
        /** <主键， Object>*/

        /** TODO: 将来这里要修改成加了id注解的字段*/

        StorageData<Object,Object> data = new StorageData<>();
        data.setValues(map);
        Storage<Object,Object> storage = new Storage<>();
        storage.setData(data);

        storageMap.put(def.getClz(), storage);
    }



    private void registResourceCacah(String loaction) {
        try {
            File file = new File(loaction);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            caches.put(loaction,inputStream);
        }catch (IOException e){
            logger.error("注册资源缓存失败");
            e.printStackTrace();
        }
    }
    private void registResourceDefintion(Class<?> clz, ResourceDefinition def) {
        if(!definitionMap.containsKey(clz)) {
            definitionMap.put(clz, def);
        }
    }

    public Map<Class<?>, ResourceDefinition> getDefinitionMap() {
        return definitionMap;
    }

    public void setDefinitionMap(Map<Class<?>, ResourceDefinition> definitionMap) {
        this.definitionMap = definitionMap;
    }

    public static Map<Class<?>, Storage<?, ?>> getStorageMap() {
        return storageMap;
    }

    public static void setStorageMap(Map<Class<?>, Storage<?, ?>> storageMap) {
        StorageManager.storageMap = storageMap;
    }

    public static Map<String, InputStream> getCaches() {
        return caches;
    }

    public static void setCaches(Map<String, InputStream> caches) {
        StorageManager.caches = caches;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

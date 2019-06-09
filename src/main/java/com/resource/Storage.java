package com.resource;

import com.resource.other.ResourceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 10:06
 */
public class Storage<K, V> extends Observable implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(Storage.class);


    /**
     * 放静态数据
     */
    private StorageData<K, V> data = new StorageData<>();

    /**
     * @param k
     * @return
     */
    public V get(K k){
        return data.getValues().get(k);
    }


    public StorageData<K, V> getData() {
        return data;
    }

    public void setData(StorageData<K, V> data) {
        this.data = data;
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}

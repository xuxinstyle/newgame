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

   /* public Collection<V> getAll(){
        //根据clz获取到Storage
        return Collections.unmodifiableCollection(data.getValues().values());
    }*/
   // 根据资源表的唯一标识id获取到List
    public V get(K k){
        //根据clz获取到Storage
        return data.getValues().get(k);
    }

    private Map<K, List<V>> dataList = new HashMap<>();

    public StorageData<K, V> getData() {
        return data;
    }

    public void setData(StorageData<K, V> data) {
        this.data = data;
    }

    public Map<K, List<V>> getDataList() {
        return dataList;
    }

    public void setDataList(Map<K, List<V>> dataList) {
        this.dataList = dataList;
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}

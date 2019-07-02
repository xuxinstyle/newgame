package com.db.cache;

import com.db.IEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/7/2 14:34
 */
public class EntityCache<K extends Serializable & Comparable<K>, T extends IEntity<K>>{
    /**
     * 弱引用map 一旦内存不够，在GC时，没有被引用的表项很快会被清除掉，从而避免系统内存溢出
     * Collections.synchronizedMap通过锁机制实现的
     */
    private Map<K,T> cache = Collections.synchronizedMap(new WeakHashMap<>());

    public boolean isCache(K k) {
        return cache.containsKey(k);
    }
    public T get(K k) {
        return cache.get(k);
    }

    public void put(K k, T t){
        cache.put(k,t);
    }
}

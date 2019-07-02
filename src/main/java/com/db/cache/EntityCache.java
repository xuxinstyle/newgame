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
     * FIXME:Collections.synchronizedMap通过锁机制实现的且使用的是对象锁 所以读取效率会比较低
     * TODO: 1.使用固定大小的map 可用ConCurrentHashMap封装成一个固定大小的map
     * TODO: 2.将来使用LRU策略实现最近最少使用的淘汰
     */
    private Map<K,T> weakCache = Collections.synchronizedMap(new WeakHashMap<>());

    public boolean isCache(K k) {
        return weakCache.containsKey(k);
    }

    public T get(K k) {
        return weakCache.get(k);
    }

    public void put(K k, T t){
        weakCache.put(k,t);
    }
}

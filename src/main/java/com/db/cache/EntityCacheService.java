package com.db.cache;

import com.db.EntityBuilder;
import com.db.IEntity;

import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/7/2 14:49
 */
public interface EntityCacheService<K extends Serializable & Comparable<K>, T extends IEntity<K>> {
    /**
     * 查找或创建
     *
     * @param entityClz
     * @param id
     * @return
     */
    T findOrCreate(Class<T> entityClz, K id, EntityBuilder<K, T> builder);
    /**
     * 查找 无则返回null
     *
     * @param entityClz
     * @param id
     * @return
     */
    T find(Class<T> entityClz, K id);

    /**
     * 持久化操作
     *
     * @param object
     */
    void saveOrUpdate(T object);

}

package com.db.cache;

import com.db.EntityBuilder;
import com.db.HibernateDao;
import com.db.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/7/2 14:49
 */
@Component
public class EntityCacheServiceImpl<K extends Serializable & Comparable<K>, T extends IEntity<K>> implements EntityCacheService<K, T> {
    @Autowired
    private HibernateDao hibernateDao;

    private Map<Class<?>, EntityCache<K,T>> entityCacheMap = new ConcurrentHashMap<>();

    @Override
    public void remove(Class<T> clz, K k) {
        EntityCache<K, T> entityCache = entityCacheMap.get(clz);
        if (entityCache == null) {
            entityCache = new EntityCache<>();
            entityCacheMap.put(clz, entityCache);
        }
        T t = hibernateDao.find(clz, k);
        if (t == null) {
            entityCache = new EntityCache<>();
            entityCacheMap.put(clz, entityCache);
        }
        entityCache.remove(k);
        hibernateDao.remove(t);
    }

    @Override
    public T findOrCreate(Class<T> entityClz, K id, EntityBuilder<K, T> builder) {
        EntityCache<K, T> entityCache = entityCacheMap.get(entityClz);
        if (entityCache == null) {
            entityCache = new EntityCache<>();
            entityCacheMap.put(entityClz, entityCache);
        }

        T t = entityCache.get(id);
        if(t == null){
            t = hibernateDao.findOrSave(entityClz,id,builder);
            entityCache.put(id,t);
        }
        return t;
    }

    @Override
    public T find(Class<T> entityClz, K id) {
        if(!entityCacheMap.containsKey(entityClz)){
            entityCacheMap.put(entityClz,new EntityCache<>());
        }
        EntityCache<K, T> entityCache = entityCacheMap.get(entityClz);
        if (entityCache == null) {
            entityCache = new EntityCache<>();
            entityCacheMap.put(entityClz, entityCache);
        }
        T t = entityCache.get(id);
        if(t==null){
            t = hibernateDao.find(entityClz, id);
            if(t == null){
                return t;
            }
            entityCache.put(id,t);
        }
        return t;
    }

    @Override
    public List<T> findAll(Class<T> entityClz) {
        EntityCache<K, T> entityCache = entityCacheMap.get(entityClz);
        if (entityCache == null) {
            entityCache = new EntityCache<>();
            entityCacheMap.put(entityClz, entityCache);
        }
        List<T> ts = hibernateDao.findAll(entityClz);
        for (T t : ts) {

            entityCache.put(t.getId(), t);
        }
        return ts;
    }

    @Override
    public void saveOrUpdate(T object) {
        if(object == null){
            throw  new RuntimeException("缓存对象被移除了");
        }
        EntityCache<K, T> entityCache = entityCacheMap.get(object.getClass());
        if (entityCache == null) {
            entityCache = new EntityCache<>();
            entityCacheMap.put(object.getClass(), entityCache);
        }
        hibernateDao.saveOrUpdate(object.getClass(),object);
        entityCache.put(object.getId(),object);
    }


}

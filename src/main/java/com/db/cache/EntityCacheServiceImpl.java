package com.db.cache;

import com.db.EntityBuilder;
import com.db.HibernateDao;
import com.db.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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
    public T findOrCreate(Class<T> entityClz, K id, EntityBuilder<K, T> builder) {
        if(!entityCacheMap.containsKey(entityClz)){
            entityCacheMap.put(entityClz,new EntityCache<>());
        }
        EntityCache<K, T> entityCache = entityCacheMap.get(entityClz);

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
    public void saveOrUpdate(T object) {
        if(object == null){
            throw  new RuntimeException("缓存对象被移除了");
        }
        if(!entityCacheMap.containsKey(object)){
            entityCacheMap.put(object.getClass(),new EntityCache<>());
        }
        EntityCache<K, T> entityCache = entityCacheMap.get(object.getClass());
        hibernateDao.saveOrUpdate(object.getClass(),object);
        entityCache.put(object.getId(),object);
    }


}

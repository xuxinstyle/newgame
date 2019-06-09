package com.db;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 22:02
 */
@Component
public class HibernateDao extends HibernateDaoSupport {
    public <PK extends Serializable,T extends IEntity> T find(Class<T> clz, PK id){
        return getHibernateTemplate().get(clz, id);
    }
    public <PK extends Serializable,T extends IEntity> PK save(Class<T> clz, T entity){
        return (PK) getHibernateTemplate().save(entity);
    }
    public <PK extends Serializable,T extends IEntity> void remove(Class<T> clz, T entity){
        getHibernateTemplate().delete(entity);
    }
    public <PK extends Serializable,T extends IEntity> void update(Class<T> clz, T entity){
        getHibernateTemplate().update(entity);
    }
    public <PK extends Serializable,T extends IEntity> void saveOrUpdate(Class<T> clz, T entity){
        getHibernateTemplate().saveOrUpdate(entity);
    }
}

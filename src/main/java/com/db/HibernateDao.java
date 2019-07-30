package com.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 22:02
 */
@Transactional(rollbackFor = {})
@Component
public class HibernateDao extends HibernateDaoSupport {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public <PK extends Serializable,T extends IEntity> T find(Class<T> clz, PK id){
        T t = getSession().get(clz, id);
        if(t==null){
            return null;
        }
        t.unserialize();
        return t;
    }

    public <PK extends Serializable, T extends IEntity> List<T> findAll(Class<T> clz) {
        List<T> ts = getHibernateTemplate().loadAll(clz);
        for (T t : ts) {
            t.unserialize();
        }
        return ts;
    }

    public <PK extends Serializable,T extends IEntity> PK save(Class<? extends IEntity> clz, T entity){
        entity.serialize();
        return (PK) getSession().save(entity);
    }

    public <PK extends Serializable,T extends IEntity> void remove(Class<T> clz, T entity){
        getSession().delete(entity);
    }

    public <PK extends Serializable,T extends IEntity> void update(Class<T> clz, T entity){

        entity.serialize();
        getSession().update(entity);
    }

    public <PK extends Serializable,T extends IEntity> void saveOrUpdate(Class<? extends IEntity> clz, T entity){
        entity.serialize();
        getSession().saveOrUpdate(entity);
    }

    public <PK extends Serializable & Comparable<PK>,T extends IEntity<PK>> T findOrSave(Class<T> clz, PK id, EntityBuilder<PK, T> builder) {
        T t = getSession().get(clz, id);

        if (t == null) {
            t = builder.newInstance(id);
            t.serialize();
            save(clz,t);
            return t;
        }

        t.unserialize();
        return t;
    }


}

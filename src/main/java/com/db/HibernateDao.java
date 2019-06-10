package com.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
        return (T)getSession().get(clz, id);
    }
    public <PK extends Serializable,T extends IEntity> PK save(Class<T> clz, T entity){
        return (PK) getSession().save(entity);
    }
    public <PK extends Serializable,T extends IEntity> void remove(Class<T> clz, T entity){
        getSession().delete(entity);
    }
    public <PK extends Serializable,T extends IEntity> void update(Class<T> clz, T entity){
        getSession().update(entity);
    }
    public <PK extends Serializable,T extends IEntity> void saveOrUpdate(Class<T> clz, T entity){
        getSession().saveOrUpdate(entity);
    }
}

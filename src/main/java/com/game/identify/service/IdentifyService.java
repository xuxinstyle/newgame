package com.game.identify.service;

import com.db.HibernateDao;
import com.game.identify.entity.IdentifyEnt;
import com.game.common.gameobject.constant.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 10:34
 */
@Component
public class IdentifyService {

    private static Logger logger = LoggerFactory.getLogger(IdentifyService.class);
    @Autowired(required = false)
    private HibernateDao hibernateDao;

    private static AtomicLong index = new AtomicLong(10000);


    /**
     * 2
     * @param type
     * @return
     */
    public synchronized long getNextIdentify(ObjectType type){
        IdentifyEnt identifyEnt = hibernateDao.find(IdentifyEnt.class, type.getTypeId());
        if(identifyEnt==null){
            identifyEnt= new IdentifyEnt();
            identifyEnt.setTypeId(type.getTypeId());
            identifyEnt.setNow(index.incrementAndGet());
            hibernateDao.saveOrUpdate(IdentifyEnt.class, identifyEnt);
            return identifyEnt.getNow();
        }
        long l = new AtomicLong(identifyEnt.getNow()).incrementAndGet();
        identifyEnt.setNow(l);
        hibernateDao.update(IdentifyEnt.class, identifyEnt);
        return l;
    }
}

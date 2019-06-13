package com.game.base.core.service;

import com.db.HibernateDao;
import com.game.base.core.entity.IdentifyEnt;
import com.game.base.gameObject.constant.ObjectType;
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
            if(logger.isDebugEnabled()){
                logger.debug("identify为null时objectId："+identifyEnt.getNow());
            }
            hibernateDao.saveOrUpdate(IdentifyEnt.class, identifyEnt);
            return identifyEnt.getNow();
        }
        long l = new AtomicLong(identifyEnt.getNow()).incrementAndGet();
        if(logger.isDebugEnabled()){
            logger.debug("identify不为null时objectId："+l);
        }
        identifyEnt.setNow(l);
        hibernateDao.update(IdentifyEnt.class, identifyEnt);
        return l;
    }
}

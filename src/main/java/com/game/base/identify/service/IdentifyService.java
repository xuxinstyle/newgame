package com.game.base.identify.service;


import com.db.cache.EntityCacheService;
import com.game.base.identify.entity.IdentifyEnt;
import com.game.base.gameobject.constant.ObjectType;
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

    @Autowired
    private EntityCacheService<Integer, IdentifyEnt> entityCacheService;

    private static AtomicLong index = new AtomicLong(10000);


    /**
     * 2
     * @param type
     * @return
     */
    public synchronized long getNextIdentify(ObjectType type){
        IdentifyEnt identifyEnt = entityCacheService.find(IdentifyEnt.class, type.getTypeId());
        if(identifyEnt==null){
            identifyEnt= new IdentifyEnt();
            identifyEnt.setTypeId(type.getTypeId());
            identifyEnt.setNow(index.incrementAndGet());
            entityCacheService.saveOrUpdate(identifyEnt);
            return identifyEnt.getNow();
        }
        long identify = new AtomicLong(identifyEnt.getNow()).incrementAndGet();
        identifyEnt.setNow(identify);
        entityCacheService.saveOrUpdate(identifyEnt);
        return identify;
    }
}

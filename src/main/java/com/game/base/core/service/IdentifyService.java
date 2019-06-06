package com.game.base.core.service;

import com.game.base.core.entity.IdentifyEnt;
import com.game.base.core.mapper.IdentifyMapper;
import com.game.base.gameObject.constant.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 10:34
 */
@Component
public class IdentifyService {

    private static Logger logger = LoggerFactory.getLogger(IdentifyService.class);

    /**
     * 1
     */
    //private static Map<Integer, AtomicLong> identifyMap = new ConcurrentHashMap<>(EntityType.values().length);
    /**
     *
     */
    private static AtomicLong index = new AtomicLong(10000);
    @Autowired
    private IdentifyMapper identifyMapper;

    /**
     * 1
     */
    /*public long getNextIdentify(EntityType type){
        if(identifyMap.get(type.getEntityId())==null){
            identifyMap.put(type.getEntityId(), new AtomicLong(10000));
        }
        AtomicLong atomicLong = identifyMap.get(type.getEntityId());
        return atomicLong.incrementAndGet();
    }*/

    /**
     * 2
     * @param type
     * @return
     */
    public synchronized long getNextIdentify(EntityType type){
        IdentifyEnt identifyEnt = identifyMapper.selectIdentifyEnt(type.getEntityId());
        if(identifyEnt==null){
            identifyEnt= new IdentifyEnt();
            identifyEnt.setTypeId(type.getEntityId());
            identifyEnt.setNow(index.incrementAndGet());
            if(logger.isDebugEnabled()){
                logger.debug("identify为null时objectId："+identifyEnt.getNow());

            }

            identifyMapper.insertIdentifyEnt(identifyEnt);

            return identifyEnt.getNow();
        }

        long l = new AtomicLong(identifyEnt.getNow()).incrementAndGet();
        if(logger.isDebugEnabled()){
            logger.debug("identify不为null时objectId："+l);
        }
        identifyEnt.setNow(l);
        identifyMapper.updateIdentifyEnt(identifyEnt);
        return l;
    }
}

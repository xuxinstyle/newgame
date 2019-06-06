package com.game.base.core.service;

import com.game.base.core.entity.IdentifyEnt;
import com.game.base.core.mapper.IdentifyMapper;
import com.game.base.gameObject.constant.EntityType;
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

    /**
     * 1
     */
    //private static Map<Integer, AtomicLong> identifyMap = new ConcurrentHashMap<>(EntityType.values().length);
    /**
     *
     */
    private static AtomicLong index = new AtomicLong(10001);
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
    public long  getNextIdentify(EntityType type){
        IdentifyEnt identifyEnt = identifyMapper.selectIdentifyEnt(type.getEntityId());
        if(identifyEnt==null){
            IdentifyEnt identifyEnt1 = new IdentifyEnt();
            identifyEnt1.setTypeId(type.getEntityId());
            identifyEnt1.setNow(index.incrementAndGet());
            identifyMapper.updateIdentifyEnt(identifyEnt1);
            return identifyEnt1.getNow();
        }
        long l = new AtomicLong(identifyEnt.getNow()).incrementAndGet();
        identifyEnt.setNow(l);
        identifyMapper.updateIdentifyEnt(identifyEnt);
        return l;
    }
}

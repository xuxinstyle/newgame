package com.game.scence.monster.service;

import com.game.SpringContext;
import com.game.scence.monster.resource.MonsterResource;
import com.game.scence.visible.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:13
 */
@Component
public class MonsterServiceImpl implements MonsterService {
    private static Logger logger = LoggerFactory.getLogger(MonsterServiceImpl.class);
    @Autowired
    private MonsterManager monsterManager;
    @Override
    public List<MonsterResource> getMapMonsterResources(int mapId){
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        List<Integer> monsterList = mapResource.getMonsterList();
        if(monsterList==null){
            if (logger.isDebugEnabled()) {
                logger.error("地图[{}]没有怪物", mapId);
            }
            return null;
        }
        List<MonsterResource> monsterResourceList = new ArrayList<>();
        for(int monsterId:monsterList){
            MonsterResource monsterResource = monsterManager.getMonsterResource(monsterId);
            if(monsterResource==null){
                continue;
            }
            monsterResourceList.add(monsterResource);
        }
        return monsterResourceList;
    }
}

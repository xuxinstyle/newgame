package com.game.scence.monster.service;

import com.game.scence.monster.resource.MonsterResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:19
 */
@Component
public class MonsterManager {
    @Autowired
    private StorageManager storageManager;

    public MonsterResource getMonsterResource(int monsterId){
        return storageManager.getResource(monsterId,MonsterResource.class);
    }

    public Collection<MonsterResource> getAllMonsterResource(){
        return (Collection<MonsterResource>)storageManager.getResourceAll(MonsterResource.class);
    }

}

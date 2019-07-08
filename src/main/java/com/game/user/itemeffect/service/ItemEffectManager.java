package com.game.user.itemeffect.service;


import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;

import com.game.user.itemeffect.command.ItemExpireDelayCommand;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 12:02
 */
@Component
public class ItemEffectManager {

    @Autowired
    private EntityCacheService<Long, ItemEffectEnt> entityCacheService;
    /**
     * <角色id，<道具表id，道具延迟命令 >>  在登出后取消已经抛出但是还没有过期的命令
     */
    private Map<Long ,Map<Integer, ItemExpireDelayCommand>> playerItemExpireCommandMap = new ConcurrentHashMap<>();

    public Map<Integer, ItemExpireDelayCommand> getItemDeprecateDelayCommandMap(long playerId){
        return playerItemExpireCommandMap.get(playerId);
    }


    public void putCommand(ItemExpireDelayCommand command){
        Map<Integer, ItemExpireDelayCommand> delayCommandMap = playerItemExpireCommandMap.get(command.getPlayerId());
        if(delayCommandMap==null){
            delayCommandMap = new ConcurrentHashMap<>();
            delayCommandMap.put(command.getItemModelId(),command);
            playerItemExpireCommandMap.put(command.getPlayerId(),delayCommandMap);
            return;
        }
        delayCommandMap.put(command.getItemModelId(),command);

    }

    public void removeDelayCommand(long playerId){
        playerItemExpireCommandMap.remove(playerId);

    }

    public void saveItemEffect(ItemEffectEnt itemEffectEnt){

        entityCacheService.saveOrUpdate(itemEffectEnt);
    }

    public ItemEffectEnt getItemEffect(long playerId){
        return entityCacheService.find(ItemEffectEnt.class,playerId);
    }

    public ItemEffectEnt getItemEffectOrCreate(long playerId){
        ItemEffectEnt itemEffectEnt = entityCacheService.findOrCreate(ItemEffectEnt.class, playerId, new EntityBuilder<Long, ItemEffectEnt>() {
            @Override
            public ItemEffectEnt newInstance(Long id) {
                return ItemEffectEnt.valueOf(playerId);
            }
        });
        return itemEffectEnt;
    }
}

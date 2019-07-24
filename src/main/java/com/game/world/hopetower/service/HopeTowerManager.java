package com.game.world.hopetower.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.world.base.entity.MapInfoEnt;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 15:46
 */
@Component
public class HopeTowerManager {
    @Autowired
    private EntityCacheService<String, MapInfoEnt> hopeTowerCacheService;

    @Autowired
    private StorageManager storageManager;

    public MapInfoEnt getOrCreateHopeTowerEnt(String accountId) {
        return hopeTowerCacheService.findOrCreate(MapInfoEnt.class, accountId, new EntityBuilder<String, MapInfoEnt>() {
            @Override
            public MapInfoEnt newInstance(String id) {
                return MapInfoEnt.valueOf(id);
            }
        });
    }

    public EntityCacheService<String, MapInfoEnt> getHopeTowerCacheService() {
        return hopeTowerCacheService;
    }

    public void setHopeTowerCacheService(EntityCacheService<String, MapInfoEnt> hopeTowerCacheService) {
        this.hopeTowerCacheService = hopeTowerCacheService;
    }
}

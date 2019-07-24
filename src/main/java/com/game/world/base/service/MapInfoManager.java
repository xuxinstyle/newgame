package com.game.world.base.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.world.base.entity.MapInfoEnt;
import com.game.world.base.model.MapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 16:49
 */
@Component
public class MapInfoManager {
    @Autowired
    private EntityCacheService<String, MapInfoEnt> mapInfoCacheService;

    public MapInfoEnt getOrCreateMapInfo(String accountId) {
        return mapInfoCacheService.findOrCreate(MapInfoEnt.class, accountId, new EntityBuilder<String, MapInfoEnt>() {
            @Override
            public MapInfoEnt newInstance(String id) {
                return MapInfoEnt.valueOf(id);
            }
        });
    }

    public void save(MapInfoEnt mapInfoEnt) {
        mapInfoCacheService.saveOrUpdate(mapInfoEnt);
    }

}

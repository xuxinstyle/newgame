package com.game.world.base.service;

import com.game.world.base.entity.MapInfoEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 16:49
 */
@Component
public class MapInfoServiceImpl implements MapInfoService {
    @Autowired
    private MapInfoManager mapInfoManager;

    @Override
    public void save(MapInfoEnt mapInfoEnt) {
        mapInfoManager.save(mapInfoEnt);
    }

    @Override
    public MapInfoEnt getMapInfoEnt(String accountId) {
        return mapInfoManager.getOrCreateMapInfo(accountId);
    }
}

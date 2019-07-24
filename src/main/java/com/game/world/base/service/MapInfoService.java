package com.game.world.base.service;

import com.game.world.base.entity.MapInfoEnt;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 16:48
 */
public interface MapInfoService {
    /**
     * 保存MapInfo
     *
     * @param mapInfoEnt
     */
    void save(MapInfoEnt mapInfoEnt);

    /**
     * 获取mapInfoEnt
     *
     * @param accountId
     * @return
     */
    MapInfoEnt getMapInfoEnt(String accountId);

}

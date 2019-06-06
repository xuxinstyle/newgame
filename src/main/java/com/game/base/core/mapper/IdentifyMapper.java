package com.game.base.core.mapper;


import com.game.base.core.entity.IdentifyEnt;
import com.game.role.player.entity.PlayerEnt;

public interface IdentifyMapper {
    /**
     * 新增IdentifyEnt数据
     * @param record
     * @return
     */
    int insertIdentifyEnt(IdentifyEnt record);

    /**
     * 查询IdentifyEnt数据
     * @param typeId
     * @return
     */
    IdentifyEnt selectIdentifyEnt(int typeId);

    /**
     * 更新player数据
     * @param record
     * @return
     */
    int updateIdentifyEnt(IdentifyEnt record);
}
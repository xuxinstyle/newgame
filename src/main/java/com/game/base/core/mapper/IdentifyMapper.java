package com.game.base.core.mapper;


import com.game.role.player.entity.PlayerEnt;

public interface IdentifyMapper {
    /**
     * 新增player数据
     * @param record
     * @return
     */
    int insertPlayerEnt(PlayerEnt record);

    /**
     * 查询player数据
     * @param player
     * @return
     */
    PlayerEnt selectPlayerEnt(long player);

    /**
     * 更新player数据
     * @param record
     * @return
     */
    int updatePlayerEnt(PlayerEnt record);
}
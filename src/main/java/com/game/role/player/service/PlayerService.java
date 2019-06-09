package com.game.role.player.service;

import com.game.role.player.entity.PlayerEnt;
import com.game.role.constant.Job;
import com.game.role.player.model.Player;

public interface PlayerService {
    /**
     * 保存玩家数据
     * @param player
     */
    void save(PlayerEnt player);

    /**
     * 向数据库查询玩家数据
     * @param playerId
     * @return
     */
    PlayerEnt getPlayer(long playerId);
    /**
     * 向数据库新增角色信息
     * @param playerEnt
     */
    void insert(PlayerEnt playerEnt);

    /**
     * 创建角色
     * @param accountId
     */
    PlayerEnt createPlayer(String accountId, Job type);

    /**
     * 获取角色
     * @param accountId
     * @return
     */
    Player getPlayer(String accountId);

}

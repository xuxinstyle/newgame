package com.game.base.player.service;

import com.game.base.player.entity.PlayerEnt;
import com.game.base.player.model.Player;
import com.game.role.constant.Job;

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


}

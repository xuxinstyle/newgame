package com.game.role.battlescore.service;

import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 16:00
 */
public interface BattleScoreService {
    /**
     * 刷新玩家战力
     *
     * @param player
     */
    void freshPlayerAttribute(Player player);
}

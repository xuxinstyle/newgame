package com.game.world.rank.service;

import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 11:09
 */
public interface RankService {
    /**
     * 初始化排行榜
     */
    void init();

    /**
     * 玩家战力变更
     *
     * @param player
     */
    void doBattleScoreChange(Player player);

    /**
     * 查看排行榜
     *
     * @param accountId
     */
    void showRankList(String accountId);

    /**
     * 添加玩家到排行榜
     *
     * @param player
     */
    void addPlayerRankInfo(Player player);

}

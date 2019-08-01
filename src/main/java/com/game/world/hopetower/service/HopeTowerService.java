package com.game.world.hopetower.service;

import com.game.scence.fight.model.PlayerUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 15:46
 */
public interface HopeTowerService {
    /**
     * 查看你希望之塔信息
     *
     * @param accountId
     */
    void showHopeTowerInfo(String accountId);

    /**
     * 做怪物死亡事件的处理
     *
     * @param mapId
     * @param sceneId
     * @param accountId
     */
    void doMonsterDead(int mapId, int sceneId, String accountId);

    /**
     * 玩家死亡后处理事件
     *
     * @param playerUnit
     */
    void doPlayerDeadEvent(PlayerUnit playerUnit);

    /**
     * 退出希望之塔
     *
     * @param accountId
     */
    void exit(String accountId);
}

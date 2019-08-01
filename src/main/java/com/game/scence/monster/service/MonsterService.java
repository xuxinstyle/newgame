package com.game.scence.monster.service;

import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.resource.MonsterResource;

import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:13
 */
public interface MonsterService {

    /**
     * 获取怪物信息
     *
     * @param monsterId
     * @return
     */
    MonsterResource getMonsterResource(int monsterId);

    /**
     * 怪物反击
     */
    void fieldMonsterAttack(String accountId);

    /**
     * 获取地图中第round波的怪物
     *
     * @param mapId
     * @param round
     * @return
     */
    Map<Long, MonsterUnit> getMonsterUnits(int mapId, int round);
}

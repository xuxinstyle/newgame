package com.game.scence.monster.service;

import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.monster.resource.MonsterResource;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:13
 */
public interface MonsterService {
    /**
     * 获取指定地图的怪物
     * @param mapId
     * @return
     */
    List<MonsterResource> getMapMonsterResources(int mapId);

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
}

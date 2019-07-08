package com.game.scence.monster.service;

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
}

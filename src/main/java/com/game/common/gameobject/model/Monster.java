package com.game.common.gameobject.model;

import com.game.common.gameobject.constant.ObjectType;
import com.game.scence.constant.SceneType;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:55
 */
public class Monster extends Creature<Monster> {
    /**
     * 怪物所在的场景类型
     */
    private SceneType currentScene;

    /**
     * 怪物
     * @return
     */
    @Override
    public String getName() {
        /** 从配置表中获取*/
        return null;
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.MONSTER;
    }
}

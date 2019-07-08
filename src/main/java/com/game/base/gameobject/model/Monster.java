package com.game.base.gameobject.model;

import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.monster.resource.MonsterResource;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:55
 */
public class Monster extends Creature<Monster> {
    /**
     * 怪物所在的场景类型
     */
    private int currentMapId;
    /**
     * 怪物配置表id
     * @return
     */
    private MonsterResource monsterResource;


    @Override
    public ObjectType getObjectType() {
        return ObjectType.MONSTER;
    }

    @Override
    public String getName() {
        return monsterResource.getName();
    }

    public int getCurrentMapId() {
        return currentMapId;
    }

    public void setCurrentMapId(int currentMapId) {
        this.currentMapId = currentMapId;
    }

    public MonsterResource getMonsterResource() {
        return monsterResource;
    }

    public void setMonsterResource(MonsterResource monsterResource) {
        this.monsterResource = monsterResource;
    }
}

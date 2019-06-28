package com.game.common.gameobject.constant;

/**
 * 用来定义游戏世界中所有的对象类型
 * @Author：xuxin
 * @Date: 2019/6/3 11:27
 */
public enum ObjectType {
    /**
     * 玩家角色
     */
    PLAYER(1),
    /**
     * NPC
     */
    NPC(2),
    /**
     * 怪物
     */
    MONSTER(3),
    /**
     * 道具
     */
    ITEM(4),
    ;

    private int typeId;

    private ObjectType(int objectId){
        this.typeId = objectId;
    }

    public int getTypeId() {
        return typeId;
    }

}

package com.game.base.core.constant;

/**
 * 实体类型
 * @Author：xuxin
 * @Date: 2019/6/5 18:38
 */
public enum EntityType {
    /**玩家*/
    PLAYER(1),
    /** 怪物*/
    MONISTER(2),
    /** NPC */
    ITEM(3),
    ;
    private int typeId;
    EntityType(int typeId){
        this.typeId = typeId;
    }
}

package com.game.base.gameObject.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:27
 */
public enum EntityType {
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
    ;

    private int entityId;
    private EntityType(int entityId){
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}

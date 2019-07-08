package com.game.scence.field.packet;

/**
 * 查看野外地图的怪物信息
 * @Author：xuxin
 * @Date: 2019/7/5 22:58
 */
public class CM_ShowMonsterInfo {
    private int mapId;
    /**
     * 怪物唯一标识id
     */
    private long monsterObjectId;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getMonsterObjectId() {
        return monsterObjectId;
    }

    public void setMonsterObjectId(long monsterObjectId) {
        this.monsterObjectId = monsterObjectId;
    }
}

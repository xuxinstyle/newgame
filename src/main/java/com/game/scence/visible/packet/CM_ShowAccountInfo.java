package com.game.scence.visible.packet;

/**
 * 查看单个玩家的详细信息
 * @Author：xuxin
 * @Date: 2019/6/6 10:11
 */
public class CM_ShowAccountInfo {
    /**
     * 玩家唯一标识id
     */
    private long objectId;
    /**
     * 当前的地图
     */
    private int mapId;

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}

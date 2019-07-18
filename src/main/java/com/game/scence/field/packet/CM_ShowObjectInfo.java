package com.game.scence.field.packet;

import com.game.base.gameobject.constant.ObjectType;

/**
 * 查看野外地图的怪物信息
 * @Author：xuxin
 * @Date: 2019/7/5 22:58
 */
public class CM_ShowObjectInfo {

    private int mapId;
    /**
     * 目标类型
     */
    private ObjectType objectType;
    /**
     * 唯一标识id
     */
    private long objectId;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }
}

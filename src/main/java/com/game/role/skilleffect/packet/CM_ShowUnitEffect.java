package com.game.role.skilleffect.packet;

import com.game.base.gameobject.constant.ObjectType;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 16:06
 */
public class CM_ShowUnitEffect {
    /**
     * 地图id
     */
    private int mapId;
    /**
     * 目标类型
     */
    private ObjectType targetType;
    /**
     * 目标id
     */
    private long targetId;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public ObjectType getTargetType() {
        return targetType;
    }

    public void setTargetType(ObjectType targetType) {
        this.targetType = targetType;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
}

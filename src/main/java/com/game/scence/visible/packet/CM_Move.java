package com.game.scence.visible.packet;

import com.game.scence.visible.model.Position;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 16:54
 */
public class CM_Move {
    /**
     * 用户id
     */
    private long playerId;
    /**
     *地图id
     */
    private int mapId;
    /**
     * 目标位置
     */
    private Position targetPos;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Position getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Position targetPos) {
        this.targetPos = targetPos;
    }
}

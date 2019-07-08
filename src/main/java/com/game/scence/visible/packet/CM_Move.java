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
    private String accountId;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Position getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Position targetPos) {
        this.targetPos = targetPos;
    }
}

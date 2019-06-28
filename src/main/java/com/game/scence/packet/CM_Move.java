package com.game.scence.packet;

import com.game.scence.model.PlayerPosition;

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
    private PlayerPosition targetPos;

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

    public PlayerPosition getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(PlayerPosition targetPos) {
        this.targetPos = targetPos;
    }
}

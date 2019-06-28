package com.game.scence.packet;

import com.game.scence.model.PlayerPosition;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 17:06
 */
public class SM_EnterMap {
    /** 移动状态 1 成功 2 失败*/
    private int status;
    /** mapId*/
    private int mapId;

    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 玩家位置
     */
    private PlayerPosition position;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

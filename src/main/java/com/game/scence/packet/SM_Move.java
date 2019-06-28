package com.game.scence.packet;

import com.game.scence.model.PlayerPosition;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 16:55
 */
public class SM_Move {
    /**
     * 移动状态 1：成功  0：失败
     */
    private int status;

    private PlayerPosition position;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }
}

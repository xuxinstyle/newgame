package com.game.scence.visible.packet;

import com.game.scence.visible.model.Position;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 16:55
 */
public class SM_Move {
    /**
     * 移动状态 1：成功  0：失败
     */
    private int status;

    private Position position;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

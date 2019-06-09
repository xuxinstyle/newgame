package com.game.scence.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 16:55
 */
public class SM_Move {
    /**
     * 移动状态 1：成功  0：失败
     */
    private int status;

    private int x;

    private int y;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

package com.game.scence.packet;

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
     * x坐标
     */
    private int x;
    /**
     * y坐标
     */
    private int y;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

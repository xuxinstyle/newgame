package com.game.scence.visible.model;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 14:34
 */
public class MonsterDef {
    /**
     * 怪物id
     */
    private int monsterId;
    /**
     * 怪物数量
     */
    private int num;
    /**
     * 初始怪物位置
     */
    private int x;

    private int y;

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

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

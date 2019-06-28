package com.game.scence.model;

/**
 * 玩家位置坐标
 * @Author：xuxin
 * @Date: 2019/6/25 14:46
 */
public class PlayerPosition {

    private int x;

    private int y;

    public static PlayerPosition valueOf(int x, int y){
        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setX(x);
        playerPosition.setY(y);
        return playerPosition;
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

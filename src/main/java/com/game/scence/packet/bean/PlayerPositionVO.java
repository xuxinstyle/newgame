package com.game.scence.packet.bean;

/**
 * 玩家位置坐标
 * @Author：xuxin
 * @Date: 2019/6/25 14:46
 */
public class PlayerPositionVO {

    private int x;

    private int y;

    public static PlayerPositionVO valueOf(int x, int y){
        PlayerPositionVO playerPositionVO = new PlayerPositionVO();
        playerPositionVO.setX(x);
        playerPositionVO.setY(y);
        return playerPositionVO;
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

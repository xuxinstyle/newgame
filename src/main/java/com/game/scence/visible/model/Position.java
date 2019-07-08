package com.game.scence.visible.model;

import com.game.SpringContext;
import com.game.scence.visible.resource.MapResource;

import java.util.Random;

/**
 * 位置坐标
 * @Author：xuxin
 * @Date: 2019/6/25 14:46
 */
public class Position {

    private int x;

    private int y;

    public static Position valueOf(int x, int y){
        Position position = new Position();
        position.setX(x);
        position.setY(y);
        return position;
    }
    public static Position randomPosition(int mapId){
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        int[][] mapcontext = mapResource.getMapcontext();
        int upY = mapcontext.length;
        int upX = mapcontext[0].length;
        int x = (int)Math.random()*(upX);
        int y = (int)Math.random()*(upY);;
        while (x>upX||y>upY||x<0||y<0||mapcontext[x][y]==1){
            x = (int)Math.random()*(upX);
            y = (int)Math.random()*(upY);
        }
        return Position.valueOf(x,y);
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

    @Override
    public String toString() {
        return "["+ x +","+ y + ']';
    }
}

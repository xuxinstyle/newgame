package com.game.scence.resource;

import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 20:47
 */
@LoadResource
public class MapResource {
    /** 这里的属性必须为public的权限*/
    /** 地图id*/
    public int id;

    public int initX;

    public int initY;
    /** 地图实际形状*/
    public String context;

    @Analyze("analyzeMap")
    private int[][] mapcontext;

    public void analyzeMap(){
        String[] mapY = this.context.split(",");
        if(mapY.length<=0) {
            return ;
        }
        String[] mapX = mapY[0].split(" ");
        int[][] mapcontext = new int[mapX.length][mapY.length];
        for (int i = 0; i < mapY.length; i++) {
            String[] mapj = mapY[i].split(" ");
            for(int j = 0; j < mapj.length ;j++){
                mapcontext[i][j] = Integer.parseInt(mapj[j]);
            }
        }
        this.mapcontext = mapcontext;
    }

    public int[][] getMapcontext() {
        return this.mapcontext;
    }

    public void setMapcontext(int[][] mapcontext) {
        this.mapcontext = mapcontext;
    }

    public int getInitX() {
        return initX;
    }

    public void setInitX(int initX) {
        this.initX = initX;
    }

    public int getInitY() {
        return initY;
    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

package com.game.scence.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:24
 */
public enum SceneType {
    NoviceVillage(1),
    FIELD(2);

    private int mapid;
    private SceneType(int mapid){
        this.mapid = mapid;
    }

    public int getMapid() {
        return mapid;
    }

    public void setMapid(int mapid) {
        this.mapid = mapid;
    }
}

package com.game.scence.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:24
 */

//TODO:加练功房和副本
public enum SceneType {
    NoviceVillage(1,"新手村"),
    FIELD(2,"野外");

    private int mapId;

    private String mapName;

    public static SceneType valueOf(int mapid){
        for(SceneType type:SceneType.values()){
            if(type.getMapId()==mapid){
                return type;
            }
        }
        return null;
    }
    private SceneType(int mapId, String mapName){
        this.mapId = mapId;
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public int getMapId() {
        return mapId;
    }

}

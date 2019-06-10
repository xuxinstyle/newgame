package com.game.scence.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:24
 */
public enum SceneType {
    NoviceVillage(1,"新手村"),
    FIELD(2,"野外");

    private int mapid;

    private String mapName;

    public static SceneType valueOf(int mapid){
        for(SceneType type:SceneType.values()){
            if(type.getMapid()==mapid){
                return type;
            }
        }
        return null;
    }
    private SceneType(int mapid, String mapName){
        this.mapid = mapid;
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public int getMapid() {
        return mapid;
    }

}

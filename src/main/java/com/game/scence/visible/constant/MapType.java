package com.game.scence.visible.constant;

import com.game.scence.base.model.AbstractScene;
import com.game.scence.base.model.NoviceVillageScene;
import com.game.scence.base.model.FieldFightScene;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:24
 */

//TODO:加练功房和副本
public enum MapType {
    /**
     * 新手村
     */
    NoviceVillage(1, "新手村",NoviceVillageScene.class){

    },
    /**
     * 野外
     */
    FIELD(2, "野外", FieldFightScene.class){

    },
    ;

    static Map<Integer, MapType> mapTypeMap = new HashMap<>();

    private int mapId;

    private String mapName;

    private Class<? extends AbstractScene> clz;

    static {
        for (MapType type:MapType.values()) {
            mapTypeMap.put(type.getMapId(),type);
        }
    }
    public AbstractScene create(){
        try{
            return clz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("生成地图实例实例["+clz.getName()+"]错误");
        }
    }
    MapType(int mapId, String mapName,Class<? extends AbstractScene> clz) {
        this.mapId = mapId;
        this.mapName = mapName;
        this.clz = clz;
    }

    public static Map<Integer, MapType> getMapTypeMap() {
        return mapTypeMap;
    }

    public static void setMapTypeMap(Map<Integer, MapType> mapTypeMap) {
        MapType.mapTypeMap = mapTypeMap;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Class<? extends AbstractScene> getClz() {
        return clz;
    }

    public void setClz(Class<? extends AbstractScene> clz) {
        this.clz = clz;
    }

    public static MapType getMapType(int mapid) {
        for (MapType type : MapType.values()) {
            if (type.getMapId() == mapid) {
                return type;
            }
        }
        return null;
    }

    public String getMapName() {
        return mapName;
    }

    public int getMapId() {
        return mapId;
    }

}

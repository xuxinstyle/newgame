package com.game.scence.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:24
 */

//TODO:加练功房和副本
public enum SceneType {
    /**
     * 新手村
     */
    NoviceVillage(1, "新手村"),
    /**
     * 野外
     */
    FIELD(2, "野外");

    static Map<Integer, SceneType> sceneTypeMap = new HashMap<>();
    private int mapId;
    private String mapName;
    static {
        for (SceneType type:SceneType.values()) {
            sceneTypeMap.put(type.getMapId(),type);
        }
    }
    SceneType(int mapId, String mapName) {
        this.mapId = mapId;
        this.mapName = mapName;
    }

    public static SceneType getSceneType(int mapid) {
        for (SceneType type : SceneType.values()) {
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

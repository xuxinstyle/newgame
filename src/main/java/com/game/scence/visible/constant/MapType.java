package com.game.scence.visible.constant;

import com.game.scence.base.model.AbstractScene;
import com.game.scence.base.model.NoviceVillageScene;
import com.game.scence.base.model.FieldFightScene;
import com.game.scence.base.model.HopeTowerScene;
import com.game.world.base.model.AbstractMapInfo;
import com.game.world.hopetower.model.HopeTowerInfo;

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
        @Override
        public int getMapId() {
            return 1;
        }
    },
    /**
     * 野外
     */
    FIELD(2, "野外", FieldFightScene.class){
        @Override
        public int getMapId() {
            return 2;
        }
    },
    HOPE_TOWER(3, "希望之塔", HopeTowerScene.class, new HopeTowerInfo()) {
        @Override
        public int getMapId() {
            return 3;
        }
    },

    ;

    static Map<Integer, MapType> mapTypeMap = new HashMap<>();

    private int id;
    /**
     * 场景地图名称
     */
    private String mapName;
    /**
     * 场景类
     */
    private Class<? extends AbstractScene> clz;
    /**
     * 场景信息类实体
     */
    private AbstractMapInfo bean;

    static {
        for (MapType type:MapType.values()) {
            mapTypeMap.put(type.getId(), type);
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

    MapType(int id, String mapName, Class<? extends AbstractScene> clz) {
        this.id = id;
        this.mapName = mapName;
        this.clz = clz;
    }

    MapType(int id, String mapName, Class<? extends AbstractScene> clz, AbstractMapInfo bean) {
        this.id = id;
        this.mapName = mapName;
        this.clz = clz;
        this.bean = bean;
    }

    public abstract int getMapId();

    public AbstractMapInfo getBean() {
        return bean;
    }

    public void setBean(AbstractMapInfo bean) {
        this.bean = bean;
    }

    public static Map<Integer, MapType> getMapTypeMap() {
        return mapTypeMap;
    }

    public static void setMapTypeMap(Map<Integer, MapType> mapTypeMap) {
        MapType.mapTypeMap = mapTypeMap;
    }

    public void setId(int id) {
        this.id = id;
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
            if (type.getId() == mapid) {
                return type;
            }
        }
        return null;
    }

    public String getMapName() {
        return mapName;
    }

    public int getId() {
        return id;
    }

}

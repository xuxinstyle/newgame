package com.game.world.base.model;

import com.game.scence.visible.constant.MapType;
import com.game.world.hopetower.model.HopeTowerInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 10:22
 */
public class MapInfo implements Serializable {
    // 《地图类型id, 地图信息》
    private Map<Integer, AbstractMapInfo> infoMap = new HashMap<>();

    public static MapInfo valueOf() {
        MapInfo mapInfo = new MapInfo();
        Map<Integer, AbstractMapInfo> mapInfoMap = new HashMap<>();
        // 初始化希望之塔
        HopeTowerInfo hopeTowerInfo = new HopeTowerInfo();
        mapInfoMap.put(MapType.HOPE_TOWER.getId(), hopeTowerInfo.valueOf());
        mapInfo.setInfoMap(mapInfoMap);
        return mapInfo;
    }

    public Map<Integer, AbstractMapInfo> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<Integer, AbstractMapInfo> infoMap) {
        this.infoMap = infoMap;
    }
}

package com.game.world.hopetower.model;

import com.game.SpringContext;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;
import com.game.util.CommonUtil;
import com.game.util.PlayerUtil;
import com.game.world.base.model.AbstractMapInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 16:31
 */
public class HopeTowerInfo extends AbstractMapInfo {
    /**
     * 正在通关的的mapId,即最顶层的mapid
     */
    private int currMapId;


    @Override
    public HopeTowerInfo valueOf() {
        HopeTowerInfo hopeTowerInfo = new HopeTowerInfo();
        hopeTowerInfo.setCurrMapId(CommonUtil.HOPE_TOWER_INIT_MAPID);
        return hopeTowerInfo;
    }

    @Override
    public boolean isPass(int mapId) {
        if (currMapId > mapId) {
            return true;
        }
        return false;
    }

    @Override
    public void openNext(int mapId) {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId + 1);
        if (mapResource == null) {
            return;
        }
        int mapType = mapResource.getMapType();
        if (mapType == MapType.HOPE_TOWER.getMapId()) {
            this.currMapId = mapId + 1;
        }

    }


    @Override
    public boolean isOpen(int mapId) {

        if (currMapId >= mapId) {
            return true;
        }
        return false;
    }


    public int getCurrMapId() {
        return currMapId;
    }

    public void setCurrMapId(int currMapId) {
        this.currMapId = currMapId;
    }
}

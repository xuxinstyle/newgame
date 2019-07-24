package com.game.world.hopetower.packet;

import java.util.Map;

/**
 * 显示希望之塔的通关信息
 *
 * @Author：xuxin
 * @Date: 2019/7/22 17:01
 */
public class SM_ShowHopeTowerInfo {
    /**
     * 希望之塔通关信息
     */
    private int currMapId;

    public int getCurrMapId() {
        return currMapId;
    }

    public void setCurrMapId(int currMapId) {
        this.currMapId = currMapId;
    }
}

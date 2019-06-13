package com.game.scence.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 21:16
 */
public class SM_OnlinePlayerOperate {
    /**所以玩家的位置信息1,2:1,2*/
    private String scenePositions;

    public String getScenePositions() {
        return scenePositions;
    }

    public void setScenePositions(String scenePositions) {
        this.scenePositions = scenePositions;
    }
}

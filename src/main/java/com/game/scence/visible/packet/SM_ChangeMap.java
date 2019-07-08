package com.game.scence.visible.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 10:13
 */
public class SM_ChangeMap {
    /**
     * 进入的地图id
     */
    private int targetmapId;
    /**
     * 上一张地图的id
     */
    private int preMapId;

    public static SM_ChangeMap valueOf(int targetmapId,int preMapId){
        SM_ChangeMap sm = new SM_ChangeMap();
        sm.setPreMapId(preMapId);
        sm.setTargetmapId(targetmapId);
        return sm;
    }
    public int getTargetmapId() {
        return targetmapId;
    }

    public void setTargetmapId(int targetmapId) {
        this.targetmapId = targetmapId;
    }

    public int getPreMapId() {
        return preMapId;
    }

    public void setPreMapId(int preMapId) {
        this.preMapId = preMapId;
    }

}

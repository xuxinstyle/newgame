package com.game.scence.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 17:06
 */
public class SM_EnterMap {
    /** 移动状态 1 成功 2 失败*/
    private int status;
    /** mapId*/
    private int mapId;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

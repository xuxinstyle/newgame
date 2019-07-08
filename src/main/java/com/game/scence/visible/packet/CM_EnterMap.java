package com.game.scence.visible.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 17:05
 * @Id
 */
public class CM_EnterMap {
    /** 前往的地图Id*/
    private int mapId;

    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}

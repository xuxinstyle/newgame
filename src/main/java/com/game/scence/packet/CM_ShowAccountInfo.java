package com.game.scence.packet;

/**
 * 查看单个玩家的详细信息
 * @Author：xuxin
 * @Date: 2019/6/6 10:11
 */
public class CM_ShowAccountInfo {
    /**
     * 账号Id
     */
    private String accountId;

    /**
     * 当前的地图
     */
    private int mapId;

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

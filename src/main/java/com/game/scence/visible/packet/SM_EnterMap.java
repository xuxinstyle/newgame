package com.game.scence.visible.packet;

import com.game.scence.visible.model.Position;

/**
 * 进入地图成功
 * @Author：xuxin
 * @Date: 2019/6/5 17:06
 */
public class SM_EnterMap {

    /** mapId*/
    private int mapId;

    /**
     * 账号Id
     */
    private String accountId;

    public static SM_EnterMap valueOf(String accountId,int targetMapId){
        SM_EnterMap sm = new SM_EnterMap();
        sm.setAccountId(accountId);
        sm.setMapId(targetMapId);
        return sm;
    }
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

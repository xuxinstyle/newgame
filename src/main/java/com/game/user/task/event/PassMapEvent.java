package com.game.user.task.event;

import com.event.event.IEvent;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 17:08
 */
public class PassMapEvent implements IEvent {

    private int mapId;

    private String accountId;

    public static PassMapEvent valueOf(String accountId, int mapId) {
        PassMapEvent event = new PassMapEvent();
        event.setMapId(mapId);
        event.setAccountId(accountId);
        return event;
    }

    @Override
    public long getId() {
        return 0;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

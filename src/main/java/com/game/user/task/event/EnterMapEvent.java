package com.game.user.task.event;

import com.event.event.IEvent;

/**
 * @Author：xuxin
 * @Date: 2019/7/28 10:56
 */
public class EnterMapEvent implements IEvent {

    private int mapId;

    private String accountId;

    public static EnterMapEvent valueOf(String accountId, int mapId) {
        EnterMapEvent event = new EnterMapEvent();
        event.setAccountId(accountId);
        event.setMapId(mapId);
        return event;
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

    @Override
    public long getId() {
        return 0;
    }
}

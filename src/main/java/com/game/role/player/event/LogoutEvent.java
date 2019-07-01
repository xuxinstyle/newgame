package com.game.role.player.event;

import com.event.event.IEvent;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 11:43
 */
public class LogoutEvent implements IEvent {

    private String accountId;

    public static LogoutEvent valueOf(String accountId){
        LogoutEvent event = new LogoutEvent();
        event.setAccountId(accountId);
        return event;
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

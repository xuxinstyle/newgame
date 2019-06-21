package com.game.login.event;

import com.event.event.IEvent;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 15:06
 */
public class LoginEvent implements IEvent {
    /**
     * 账号Id
     */
    private String accountId;

    public static LoginEvent valueOf(String accountId){
        LoginEvent event = new LoginEvent();
        event.setAccountId(accountId);
        return event;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

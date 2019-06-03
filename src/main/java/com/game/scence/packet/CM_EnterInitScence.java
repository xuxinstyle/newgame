package com.game.scence.packet;

import com.game.scence.constant.SceneType;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:58
 * @id 10
 */
public class CM_EnterInitScence {
    /**
     * 账号Id
     */
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

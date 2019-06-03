package com.game.scence.packet;

import com.game.scence.constant.SceneType;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 18:16
 */
public class SM_EnterInitScence {
    /**
     * 场景类型
     */
    private SceneType type;
    /**
     * 账号Id
     */
    private String accountId;

    public SceneType getType() {
        return type;
    }

    public void setType(SceneType type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

package com.game.scence.packet;

import com.game.scence.constant.SceneType;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 18:16
 * @id 11
 */
public class SM_EnterInitScence {
    /**
     * 场景类型
     */
    private int type;
    /**
     * 账号Id
     */
    private String accountId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

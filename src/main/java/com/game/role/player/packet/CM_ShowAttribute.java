package com.game.role.player.packet;

/**
 * 请求查看玩家属性信息
 * @Author：xuxin
 * @Date: 2019/6/17 11:40
 */
public class CM_ShowAttribute {
    /**
     * 玩家账号id
     */
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

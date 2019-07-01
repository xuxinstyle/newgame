package com.game.role.equip.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 21:46
 */
public class CM_ShowEquipInfo {
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

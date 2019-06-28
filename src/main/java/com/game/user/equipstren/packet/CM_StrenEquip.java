package com.game.user.equipstren.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 9:08
 */
public class CM_StrenEquip {
    /**
     * 道具唯一id
     */
    private long itemObjectId;
    /**
     * 玩家账号id
     */
    private String accountId;

    public long getItemObjectId() {
        return itemObjectId;
    }

    public void setItemObjectId(long itemObjectId) {
        this.itemObjectId = itemObjectId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

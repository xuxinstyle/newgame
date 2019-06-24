package com.game.user.item.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 16:40
 */
public class CM_ShowItemInfo {
    /**
     * 道具唯一id
     */
    private long itemObjectId;
    /**
     * 玩家id
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

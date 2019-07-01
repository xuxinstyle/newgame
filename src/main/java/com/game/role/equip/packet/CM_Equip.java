package com.game.role.equip.packet;

/**
 * 穿装备
 * @Author：xuxin
 * @Date: 2019/6/16 11:12
 */
public class CM_Equip {

    private String accountId;
    /**
     * 道具唯一id
     */
    private long itemObjectId;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getItemObjectId() {
        return itemObjectId;
    }

    public void setItemObjectId(long itemObjectId) {
        this.itemObjectId = itemObjectId;
    }
}

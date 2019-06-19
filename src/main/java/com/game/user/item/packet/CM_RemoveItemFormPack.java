package com.game.user.item.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 12:02
 */
public class CM_RemoveItemFormPack {
    /**
     * 玩家iD
     */
    private String accountId;
    /**
     * objectId
     */
    private long objectId;
    /**
     * 数量
     */
    private int num;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

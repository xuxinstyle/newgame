package com.game.user.item.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 11:48
 */
public class CM_AwardToPack {
    /**
     * 玩家账号Id
     */
    private String accountId;
    /**
     * 道具表中的Id
     */
    private int itemModelId;

    /**
     * 数量
     */
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
}

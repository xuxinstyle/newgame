package com.game.user.equipstren.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 9:09
 */
public class SM_StrenEquip {
    /**
     * 强化状态 1 强化成功 2 背包中没有该装备 3 道具不足  4 该道具不是装备 5 达到强化等级上限
     */
    private int status;
    /**
     * 道具名
     */
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

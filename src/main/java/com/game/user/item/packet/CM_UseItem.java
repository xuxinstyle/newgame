package com.game.user.item.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 9:18
 */
public class CM_UseItem {
    /**
     * 道具唯一id
     */
    private long itemObjectId;

    /**
     *
     * 使用数量
     */
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getItemObjectId() {
        return itemObjectId;
    }

    public void setItemObjectId(long itemObjectId) {
        this.itemObjectId = itemObjectId;
    }
}

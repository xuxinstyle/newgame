package com.game.user.item.packet.bean;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 14:50
 */
public class ItemVO {
    /**
     * 道具唯一id
     */
    private long objectId;
    /**
     * 道具表唯一id
     */
    private int itemModelId;
    /**
     * 道具名
     */
    private String itemName;
    /**
     * 道具数量
     */
    private int num;

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {

        this.itemModelId = itemModelId;
    }

    @Override
    public String toString() {
        return "ItemVO{" +
                "objectId=" + objectId +
                ", itemModelId=" + itemModelId +
                ", itemName='" + itemName + '\'' +
                ", num=" + num +
                '}';
    }
}

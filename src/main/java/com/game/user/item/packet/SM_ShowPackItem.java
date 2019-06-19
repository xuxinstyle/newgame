package com.game.user.item.packet;

import com.game.user.item.packet.bean.ItemVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 14:47
 */
public class SM_ShowPackItem {
    /**
     * 背包大小
     */
    private int size;
    /**
     * 背包已使用格子数
     */
    private int useSize;
    /**
     * 道具列表
     */
    private List<ItemVO> itemList;

    public List<ItemVO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemVO> itemList) {
        this.itemList = itemList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUseSize() {
        return useSize;
    }

    public void setUseSize(int useSize) {
        this.useSize = useSize;
    }
}

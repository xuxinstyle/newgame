package com.game.user.item.model;

import com.game.SpringContext;
import com.game.user.item.resource.ItemResource;

/**
 * @Author：xuxin
 * @Date: 2019/6/10 21:46
 */
public class ItemStorageInfo {
    /**
     * 背包最大格子数
     */
    private int maxSize;
    /**
     * 背包已经使用了的格子数
     */
    private int useSize;

    /**
     * 背包中的所有道具,由于序列化的问题不使用List
     */
    private AbstractItem[] items;

    public static ItemStorageInfo valueOf(int maxSize, int initUsedSize) {
        ItemStorageInfo itemStorageInfo = new ItemStorageInfo();
        itemStorageInfo.setMaxSize(maxSize);
        itemStorageInfo.setItems(new AbstractItem[maxSize]);
        itemStorageInfo.setUseSize(initUsedSize);
        return itemStorageInfo;
    }

    /**
     * 添加道具到背包中 FIXME:没有判断背包是否满 使用前需要判断背包空间是否充足
     * 这里的添加道具会堆叠
     *
     * @param addItem
     */
    public boolean addItem(AbstractItem addItem) {
        /**
         *  先找是否有相同的道具类型
         *  1.1 找到相同的道具类型了
         *      判断是否放得下
         *          1.1.1 放得下就放下
         *          1.1.2 放不下先填满当前的位置再继续找相同的道具类型回到 1
         *  1.2 找不到相同类型的道具
         *      找空位置
         *          1.2.1 可以放完道具直接放，
         *          1.2.2 不可以就回到继续回到 1
         */

        int position = findSameItem(addItem);
        if(position!=-1){
            int defNum = items[position].getOverLimit() - items[position].getNum();
            if(addItem.getNum()<=defNum){
                items[position].setNum(items[position].getNum()+addItem.getNum());
            }else{
                items[position].setNum(items[position].getOverLimit());
                addItem.setNum(addItem.getNum()-defNum);
                addItem(addItem);
            }
        }else{
            int emptyPosition = findEmpty();
            if(emptyPosition==-1){
                return false;
            }
            if(addItem.getNum()<=addItem.getOverLimit()){
                items[emptyPosition] = addItem;
                useSize++;
            }else{
                AbstractItem copy = addItem.copy();
                copy.setNum(copy.getNum()-copy.getOverLimit());
                addItem.setNum(addItem.getOverLimit());
                items[emptyPosition] = addItem;
                useSize++;
                addItem(copy);
            }
        }
        return true;
    }

    private int findEmpty() {
        for (int i = 0; i < items.length; i++) {
            if(items[i]==null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 找到背包中第一个可以堆叠防置的格子并返回位置参数
     * @param addItem
     * @return
     */
    private int findSameItem(AbstractItem addItem) {
        for (int i = 0; i < items.length; i++) {
            if(items[i]!=null) {
                if (addItem.getItemModelId() == items[i].getItemModelId()&& items[i].getNum()<items[i].getOverLimit()){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 找出所有可以放这个道具的最大数量
     *
     * @param addItem
     * @return
     */
    public boolean checkPackEnough(AbstractItem addItem) {
        /**
         * 找出所有可以放这个道具的最大数量
         */
        ItemResource itemResource = SpringContext.getItemService().getItemResource(addItem.getItemModelId());
        if (itemResource.isAutoUse()) {
            return true;
        }
        int allNum = 0;
        for(int i = 0;i<items.length;i++){
            if (items[i] == null) {
                allNum += addItem.getOverLimit();
                continue;
            }
            if(items[i].getItemModelId()==addItem.getItemModelId()){
                allNum+=items[i].getOverLimit()-items[i].getNum();
            }

        }
        if(allNum>=addItem.getNum()){
            return true;
        }else {
            return false;
        }
    }


    /**
     * 根据道具的objectId移除
     *
     * @param objectId
     * @param num
     */
    public boolean removeByObject(long objectId, int num) {
        /**
         * 1.可能数量不够
         * 2.可能数量够 但是不在同一个格子中
         * 3.数量够且在同一个格子中 FIXME:如果需要整理背包可以另外添加一个命令整理背包
         * 4.在不同的格子中就表示格子的不是同一个objectid 所以不需要同一种道具但是不同格子的情况
         */
        for (int i = 0; i < items.length; i++) {
            if(items[i]==null){
                continue;
            }
            if (items[i].getObjectId() == objectId) {
                if (items[i].getNum() < num) {
                    return false;
                }
                if (items[i].getNum() == num) {
                    items[i] = null;
                    useSize--;
                    return true;
                }
                items[i].reduceNum(num);
                return true;
            }
        }
        return false;
    }

    /**
     * 根据背包中格子对应的位置移除
     *
     * @param gridId
     * @param num
     */
    public boolean removeByGridId(int gridId, int num) {
        if (items[gridId] == null) {
            return false;
        }
        if (items[gridId].getNum() < num) {
            return false;
        }
        if (items[gridId].getNum() == num) {
            items[gridId] = null;
            useSize--;
            return false;
        }
        items[gridId].reduceNum(num);
        return true;
    }

    public void removeItem(int itemModelId, int num) {
        int sum = num;
        for (int i = 0; i < maxSize; i++) {
            if (items[i] == null || items[i].getItemModelId() != itemModelId) {
                continue;
            }
            if (items[i].getNum() > sum) {
                items[i].reduceNum(sum);
                return;
            }
            if (items[i].getNum() <= sum) {
                sum -= items[i].getNum();
                items[i] = null;
                useSize--;
            }
        }
    }

    /**
     * FIXME:这里的整理，不会合并同类项
     * 只是将前面空的格子填满 等需要背包排序的时候再合并同类型，否则效率很低
     */
    public void sort() {
        /**
         * 1 合并同类项
         */
        /**
         * 2.将前面空的格子填满
         */
        AbstractItem[] itemCopy = new AbstractItem[maxSize];
        int curr = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                itemCopy[curr++] = items[i];
            }
        }
        items = itemCopy;

    }

    public AbstractItem getItem(long itemObjectId) {
        for (AbstractItem item : items) {
            if (item == null) {
                continue;
            }
            if (item.getObjectId() == itemObjectId) {
                return item;
            }
        }
        return null;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getUseSize() {
        return useSize;
    }

    public void setUseSize(int useSize) {
        this.useSize = useSize;
    }

    public AbstractItem[] getItems() {
        return items;
    }

    public void setItems(AbstractItem[] items) {
        this.items = items;
    }
}

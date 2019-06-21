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

    /**
     * TODO:这里先写死背包的格子数  本来应该写在配置表中的，等以后增加了公共配置表 再换上
     */
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

        if (addItem.getOverLimit() <= 1) {
            while (addItem.getNum() > 0) {
                if (addItem.getNum() == 1) {
                    addEmpty(addItem);
                    break;
                }
                AbstractItem copy = addItem.copy();
                copy.setNum(1);
                addItem.reduceNum(1);
                addEmpty(copy);
            }
            return true;
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                continue;
            }
            /**判断是否是同一类的道具*/
            if (items[i].getItemModelId() == addItem.getItemModelId()) {
                /** 判断加入的道具是否在加入后会增加使用的格子*/
                if (!items[i].addNumAndCheck(addItem.getNum())) {
                    /**TODO:这里需要加一个整理背包的操作*/
                    if (useSize + 1 > maxSize) {
                        return false;
                    }
                    int deff = items[i].getOverLimit() - items[i].getNum();
                    items[i].setNum(items[i].getOverLimit());
                    addItem.reduceNum(deff);
                    // TODO:--------------------
                    while (addItem.getNum() > addItem.getOverLimit()) {
                        AbstractItem copy = addItem.copy();
                        copy.setNum(copy.getOverLimit());
                        addItem.reduceNum(addItem.getOverLimit());
                        addEmpty(copy);
                    }
                    addEmpty(addItem);
                    return true;
                } else {
                    return true;
                }
            }
        }
        while (addItem.getNum() > addItem.getOverLimit()) {
            AbstractItem copy = addItem.copy();
            copy.setNum(copy.getOverLimit());
            addItem.reduceNum(addItem.getOverLimit());
            addEmpty(copy);
        }
        /**TODO:这里需要加整理背包操作*/
        addEmpty(addItem);
        return true;
    }

    public void addEmpty(AbstractItem item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].getNum() == 0) {
                items[i] = item;
                useSize++;
                return;
            }
        }
    }


    /**
     * 判断玩家背包中加入这个道具是否会满
     * 1.判断背包中是否有相同的道具可以合并
     * 找出有几个可以合并的道具
     * 可合并的话合并后是否会超过
     *
     * @param addItem
     * @return
     */
    public boolean checkPackEnough(AbstractItem addItem) {
        ItemResource itemResource = SpringContext.getItemService().getItemResource(addItem.getItemModelId());
        if (itemResource.isAutoUse()) {
            return true;
        }
        int num1 = addItem.getNum() / addItem.getOverLimit();
        int num2 = addItem.getNum() % addItem.getOverLimit() == 0 ? 0 : 1;
        if (num1 + num2 > maxSize - useSize) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * p判断不需要将道具叠加到其他道具上的时候背包是否充足
     */
    private boolean diffCheckEnough(AbstractItem addItem, int currNum) {
        int mod = currNum % addItem.getOverLimit();
        int q = currNum / addItem.getOverLimit();
        if (mod > 0) {
            if (useSize + q + 1 > maxSize) {
                return false;
            }
            return true;
        } else {
            if (useSize + q > maxSize) {
                return false;
            }
            return true;
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
     * 这里的整理，不会合并同类项
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

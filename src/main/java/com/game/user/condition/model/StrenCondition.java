package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 12:16
 */
public class StrenCondition extends Condition{
    /**
     * 消耗道具id
     */
    private int itemModelId;
    /**
     * 消耗道具数量
     */
    private int num;

    @Override
    public boolean checkCondition(String accountId) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        AbstractItem[] items = itemStorageEnt.getPack().getItems();
        for(AbstractItem item:items){
            if(item==null){
                continue;
            }
            if(item.getItemModelId()!=this.itemModelId){
                continue;
            }
            if(item.getNum()<this.num){
                return false;
            }
        }
        return true;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.ItemStorageInfo;

import java.util.Map;

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
    public boolean checkCondition(String accountId,Map<String, Object> param) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        AbstractItem[] items = itemStorageEnt.getPack().getItems();
        /**
         * 可使用数量
         */
        int useNum = 0;
        for(AbstractItem item:items){
            if(item==null){
                continue;
            }
            if(item.getItemModelId()!=this.itemModelId){
                continue;
            }
            useNum += item.getNum();
            if(useNum >= this.num){
                return true;
            }
        }
        return false;
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

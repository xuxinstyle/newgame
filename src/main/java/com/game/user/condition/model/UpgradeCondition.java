package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 12:17
 */
public class UpgradeCondition extends Condition{
    /**
     * 消耗道具id
     */
    private int itemModelId;
    /**
     * 消耗道具数量
     */
    private int num;
    /**
     * 装备升阶所需要的等级
     */
    private int equipNeedLevel;

    @Override
    public boolean checkCondition(String accountId,int equipLevel) {
        if(equipLevel<this.equipNeedLevel){
            return false;
        }
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        AbstractItem[] items = itemStorageEnt.getPack().getItems();
        int haveNum = 0;
        for(AbstractItem item:items){
            if(item==null){
                continue;
            }
            if(item.getItemModelId()!=this.itemModelId){
                continue;
            }
            haveNum += item.getNum();
            if(haveNum>=this.num){
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

    public int getEquipNeedLevel() {
        return equipNeedLevel;
    }

    public void setEquipNeedLevel(int equipNeedLevel) {
        this.equipNeedLevel = equipNeedLevel;
    }
}

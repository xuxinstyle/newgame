package com.game.user.item.model;

import com.game.SpringContext;
import com.game.user.item.resource.ItemResource;
import com.game.user.itemeffect.model.AbstractUseEffect;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 9:51
 */
public class Medicine extends AbstractItem{

    @Override
    public AbstractItem copy() {
        Medicine medicine = new Medicine();
        medicine.setObjectId(createItemObjectId());
        medicine.setNum(this.num);
        medicine.setItemModelId(this.itemModelId);
        medicine.setItemType(this.itemType);
        return medicine;
    }

    /**
     * TODO:添加药品的使用效果的方法
     *
     */
    @Override
    public void use(String accountId,int num){
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        AbstractUseEffect useEffect = itemResource.getUseEffect();
        useEffect.active(accountId,num);

    }
}

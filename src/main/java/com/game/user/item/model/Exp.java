package com.game.user.item.model;

import com.game.SpringContext;
import com.game.user.item.resource.ItemResource;
import com.game.user.itemeffect.model.ExpUseEffect;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 18:00
 */
public class Exp extends AbstractItem{


    @Override
    public AbstractItem copy() {
        Exp exp = new Exp();
        exp.setObjectId(createItemObjectId());
        exp.setNum(this.num);
        exp.setDeprecatedTime(this.deprecatedTime);
        exp.setItemModelId(this.itemModelId);
        exp.setItemType(this.itemType);
        exp.setStatus(this.status);
        return exp;
    }

    /** 根据itemResource表初始化*/
    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);
    }

    @Override
    public void use(String accountId,int num) {
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        ExpUseEffect useEffect = (ExpUseEffect)itemResource.getUseEffect();
        useEffect.use(accountId,num);
    }
}

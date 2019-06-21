package com.game.user.item.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.user.item.resource.ItemResource;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 18:00
 */
public class Exp extends AbstractItem{


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

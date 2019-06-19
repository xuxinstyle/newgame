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
    public void init(ItemResource itemResource, Map<String, Object> params) {
        super.init(itemResource, params);
    }

    @Override
    public void use(String accountId) {
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        ExpUseEffect useEffect = (ExpUseEffect)itemResource.getUseEffect();

        useEffect.setAddExp(this.num*useEffect.getAddExp());
        useEffect.use(accountId);
    }
}

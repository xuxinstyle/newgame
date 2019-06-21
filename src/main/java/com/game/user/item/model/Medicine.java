package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.base.attribute.constant.AttributeType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.item.resource.ItemResource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 9:51
 */
public class Medicine extends AbstractItem{
    /**
     * 使用效果的持续时间
     */
    private long delay;

    /**
     * TODO:添加药品的使用效果的方法
     *
     */
    @Override
    public void use(String accountId,int num){
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        UseEffect useEffect = itemResource.getUseEffect();
        useEffect.use(accountId,num);
    }
}

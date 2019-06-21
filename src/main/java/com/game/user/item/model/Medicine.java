package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.base.attribute.constant.AttributeType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.item.resource.ItemResource;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import com.game.user.itemeffect.model.ItemEffectInfo;
import com.game.user.itemeffect.model.ItemEffectdetaiInfo;
import com.game.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        medicine.setDeprecatedTime(this.deprecatedTime);
        medicine.setItemModelId(this.itemModelId);
        medicine.setItemType(this.itemType);
        medicine.setStatus(this.status);
        return medicine;
    }

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

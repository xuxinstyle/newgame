package com.game.role.player.model;

import com.game.SpringContext;
import com.game.base.attribute.container.AttributeUpdateSet;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.role.battlescore.event.ChangeAttrbuteEvent;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:29
 */

public class PlayerAttributeContainer extends CreatureAttributeContainer {
    public PlayerAttributeContainer(){

    }

    @Override
    protected void recompute(AttributeUpdateSet updateSet) {
        /**
         * 计算新的属性
         */
        compute(modelAttributes,finalAttributes, updateSet);

    }




}

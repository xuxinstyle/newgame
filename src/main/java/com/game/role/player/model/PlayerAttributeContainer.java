package com.game.role.player.model;

import com.game.base.attribute.*;
import com.game.base.attribute.constant.AttributeType;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:29
 */

public class PlayerAttributeContainer extends CreatureAttributeContainer  {
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

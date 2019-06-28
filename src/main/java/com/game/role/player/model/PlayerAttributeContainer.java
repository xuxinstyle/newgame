package com.game.role.player.model;

import com.game.common.attribute.*;
import com.game.common.attribute.constant.AttributeType;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:29
 */

public class PlayerAttributeContainer extends CreatureAttributeContainer  {
    public PlayerAttributeContainer(){

    }

    @Override
    protected void recompute(AttributeUpdateSet records) {
        /**
         * 计算新的属性
         */
        compute(modelAttributes,finalAttributes,records);
    }



    /**
     * 强克隆map
     * @param cloneTarget
     * @param result
     */
    private void cloneAttributeMap(Map<AttributeType, Attribute> cloneTarget, Map<AttributeType, Attribute> result) {
        for(Attribute attribute:result.values()){
            attribute.setValue(0);
        }
        for (Attribute attr:cloneTarget.values()){
            Attribute attribute = result.get(attr.getAttributeType());
            if(attribute != null){
                attribute.setValue(attr.getValue());
            }else{
                result.put(attr.getAttributeType(), Attribute.valueOf(attr.getAttributeType(), attr.getValue()));
            }
        }
    }
}
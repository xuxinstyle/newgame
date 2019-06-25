package com.game.base.attribute.util;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 22:10
 */
public class ComputerUtil {
    /**
     * 计算二级属性真实值
     * @param accumulateAttributes
     * @param attr 需要计算的属性，如果不是二级属性直接返回accumulateAttributes中的值
     */
    public static long computerReal(Map<AttributeType, Attribute> accumulateAttributes, Attribute attr) {
        long value = 0L;
        AttributeType[] firstAttributes = attr.getAttributeType().getFirstAttributes();
        if(firstAttributes==null||firstAttributes.length==0){
            Attribute attribute = accumulateAttributes.get(attr.getAttributeType());
            if(attribute!=null){
                value+=attribute.getValue();
            }
            return value;
        }
        for(AttributeType type:firstAttributes){
            Attribute attribute = accumulateAttributes.get(type);
            Map<AttributeType, Attribute> attributeMap = type.computeChangeAttribute(attribute.getValue());
            if(attributeMap==null){
                continue;
            }
            Attribute changeAttr = attributeMap.get(attr.getAttributeType());
            if(changeAttr==null){
                continue;
            }
            value+=changeAttr.getValue();
        }
        Attribute attribute = accumulateAttributes.get(attr.getAttributeType());
        if(attribute!=null){
            value+=attribute.getValue();
        }
        /**
         * TODO:计算百分比属性后的值
         */



        return value;
    }

}

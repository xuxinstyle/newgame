package com.game.common.attribute;

import com.game.common.attribute.constant.AttributeType;
import com.game.common.attribute.util.AttributeUtil;

import java.util.Map;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:25
 */

public class CreatureAttributeContainer<T> extends AbstractAttributeContainer {


    @Override
    protected void recompute(AttributeUpdateSet records) {
        compute(modelAttributes,finalAttributes, records);
    }

    /**
     * 重新计算属性
     * @param modelAttributeSet
     * @param finalAttributes
     * @param records
     */
    protected void compute(Map<String,AttributeSet> modelAttributeSet, Map<AttributeType,Attribute> finalAttributes,
                         AttributeUpdateSet records) {
        /**
         * 清空 accumulateAttributes
         */
        for(Attribute attr: collectAttributes.values()){
            attr.setValue(0);
        }
        /**
         * 把所有同类型的属性加起来
         */
        AttributeUtil.collectAttribute(modelAttributeSet, collectAttributes,null);
        /**
         * 计算真实值 并将属性放到finalAttribute中
         */
        if(records!=null){
            computeRealValue(finalAttributes, records.getTypes());
        }else{
            computeRealValue(finalAttributes, collectAttributes.keySet());
        }

    }

    private void computeRealValue(Map<AttributeType, Attribute> finalAttributes, Set<AttributeType> typelist) {
        for(AttributeType type:typelist){
            if(type==null){
                continue;
            }
            Attribute attr = collectAttributes.get(type);
            if(attr==null){
                continue;
            }
            /**
             * 是二级属性的话 受其他属性的影响，所以需要计算一级属性后加进来
             */
            AttributeType[] firstAttributes = type.getFirstAttributes();
            if(firstAttributes!=null){
                for(AttributeType attributeType:firstAttributes){
                    if(attributeType==null){
                        continue;
                    }
                    Attribute attribute = collectAttributes.get(attributeType);
                    if(attribute==null){
                        continue;
                    }
                    Map<AttributeType, Attribute> attributeMap = attributeType.computeChangeAttribute(attribute.getValue());
                    if(attributeMap==null){
                        continue;
                    }
                    Attribute addAttribute = attributeMap.get(type);
                    long realValue = 0L;
                    if(addAttribute==null){
                        realValue = attr.getValue();
                    }else {
                        realValue = attr.getValue() + addAttribute.getValue();
                    }
                    finalAttributes.put(type,Attribute.valueOf(type,realValue));
                }
                continue;
            }

            /**
             * 一级属性或不被影响的二级属性直接覆盖原来的数据
             */

            /**
             * 百分比属性也直接覆盖原来的属性
             */
            finalAttributes.put(type, Attribute.valueOf(type, attr.getValue()));
        }
        /**
         * 百分比属性
         */
        for(AttributeType type:typelist){
            AttributeType[] calculateAttributes = type.getcalculateAttributes();
            if(calculateAttributes==null||calculateAttributes.length==0){
                continue;
            }
            for (AttributeType attributeType:calculateAttributes){
                Attribute attribute = finalAttributes.get(attributeType);
                if(attribute==null){
                    continue;
                }
                Attribute attr = finalAttributes.get(type);
                if(attr==null){
                    continue;
                }
                long readValue = (long)(attribute.getValue()*(1+attr.getValue()/100.0));
                finalAttributes.put(attributeType,Attribute.valueOf(attributeType,readValue));
            }
        }
    }

}
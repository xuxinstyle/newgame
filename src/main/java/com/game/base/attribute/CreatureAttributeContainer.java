package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.util.AttributeUtil;
import com.game.base.gameobject.model.Creature;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.Map;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:25
 */

public class CreatureAttributeContainer extends AttributeContainer {


    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSync) {
        compute(moduleAttributeSet,finalAttributes, records);
    }

    /**
     * 重新计算属性
     * @param modelAttributeSet
     * @param finalAttributes
     * @param records
     */
    protected void compute(Map<String,AttributeSet> modelAttributeSet, Map<AttributeType,Attribute> finalAttributes,
                         AttributeUpdateRecords records) {
        /**
         * 清空 accumulateAttributes
         */
        for(Attribute attr:accumulateAttributes.values()){
            attr.setValue(0);
        }
        /**
         * 把所有同类型的属性加起来
         */
        AttributeUtil.accumulateToMapWithExclude(modelAttributeSet, accumulateAttributes,null);
        /**
         * 计算真实值
         */
        if(records!=null){
            computeRealValue(finalAttributes, records.getTypes());
        }else{
            computeRealValue(finalAttributes, accumulateAttributes.keySet());
        }

    }

    private void computeRealValue(Map<AttributeType, Attribute> finalAttributes, Set<AttributeType> typelist) {
        for(AttributeType type:typelist){
            if(type==null){
                continue;
            }
            Attribute attr = accumulateAttributes.get(type);
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
                    Attribute attribute = accumulateAttributes.get(attributeType);
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
            AttributeType[] calculateAttributes = type.getCalculateAttributes();
            if(calculateAttributes==null||calculateAttributes.length==0){
                continue;
            }
            for (AttributeType attributeType:calculateAttributes){
                Attribute attribute = finalAttributes.get(attributeType);
                if(attribute==null){
                    continue;
                }
                Attribute attr = finalAttributes.get(type);
                long readValue = (long)(attribute.getValue()*(1+attr.getValue()/100.0));
                finalAttributes.put(attributeType,Attribute.valueOf(attributeType,readValue));
            }
        }
    }

}

package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.util.AttributeUtil;
import com.game.base.attribute.util.ComputerUtil;
import com.game.base.gameobject.model.Creature;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:25
 */
public class CreatureAttributeContainer<T extends Creature> extends AttributeContainer<T> {
    public CreatureAttributeContainer(T owner){
        super(owner);
    }

    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSync) {
        compute(modelAttributeSet,finalAttributes, records);
    }

    protected void compute(Map<AttributeId,AttributeSet> modelAttributeSet, Map<AttributeType,Attribute> finalAttributes,
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
         * 计算全部属性：将一级属性转换为二级属性，最后计算百分比属性
         */
        /**
         * 1.将一级属性转换为二级属性
         */
        for(Attribute attr:accumulateAttributes.values()){
            /**
             * 不是二级属性或二级属性不受其他属性的影响
             */
            if(attr.getAttributeType().getFirstAttributes()==null||attr.getAttributeType().getFirstAttributes().length==0){
                Attribute attribute = finalAttributes.get(attr.getAttributeType());
                if(attribute==null){
                    finalAttributes.put(attr.getAttributeType(),Attribute.valueOf(attr.getAttributeType(),attr.getValue()));
                }else {
                    attribute.setValue(attr.getValue());
                }
            }
            /**
             * 是二级属性，并且受其他属性影响，计算并获得影响后的值放进finalAttributes中
             */
            long value = ComputerUtil.computerReal(accumulateAttributes, attr);
            Attribute attribute = finalAttributes.get(attr.getAttributeType());
            if(attribute==null){
                finalAttributes.put(attr.getAttributeType(),Attribute.valueOf(attr.getAttributeType(),value));
            }else{
                attribute.setValue(attribute.getValue());
            }
        }

    }

}

package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;
import com.game.base.gameobject.model.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FIXME:注意每次计算属性后都要保存
 *
 * @Author：xuxin
 * @Date: 2019/6/12 17:19
 */
public class AttributeContainer<T extends Creature> {

    private static final Logger logger = LoggerFactory.getLogger(AttributeContainer.class);

    /**
     * 一级属性         （和百分比属性）
     */
    private Map<AttributeType, Attribute> firstAttributeMap = new HashMap<>();
    /**
     * 百分比属性  penetation
     */
    private Map<AttributeType, Attribute> percentageAttributeMap = new HashMap<>();

    /**
     * 二级属性
     */
    private Map<AttributeType, Attribute> secondAttributeMap = new HashMap<>();

    /**
     * 累加计算一级属性后的二级属性
     */
    private Map<AttributeType, Attribute> addSecondAttributeMap = new HashMap<>();

    /**
     * 计算百分比属性后的二级属性
     *
     * @param attribute
     */
    private Map<AttributeType, Attribute> computeAttributeMap = new HashMap<>();

    public AttributeContainer() {

    }

    public static AttributeContainer valueOf() {
        AttributeContainer attributeContainer = new AttributeContainer();
        Map<AttributeType, Attribute> firstAttribute = new HashMap<>();
        Map<AttributeType, Attribute> otherAttribute = new HashMap<>();
        Map<AttributeType, Attribute> percentageAttribute = new HashMap<>();
        Map<AttributeType, Attribute> computeAttribute = new HashMap<>();
        Map<AttributeType, Attribute> allAttribute = new HashMap<>();
        for (AttributeType attributeType : AttributeType.values()) {
            if (attributeType.getAttrType() == AttributeType.MAX_HP.getAttrType()) {
                firstAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
            } else if(attributeType.getAttrType() == 2){
                if (attributeType == AttributeType.ATTACK_SPEED) {
                    percentageAttribute.put(attributeType, Attribute.valueOf(attributeType, 1));
                    computeAttribute.put(attributeType, Attribute.valueOf(attributeType, 1));
                    allAttribute.put(attributeType, Attribute.valueOf(attributeType, 1));
                } else {
                    percentageAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
                    computeAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
                    allAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
                }
            } else if(attributeType.getAttrType() == 3){
                otherAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
            }
        }

        attributeContainer.setFirstAttributeMap(firstAttribute);
        attributeContainer.setPercentageAttributeMap(otherAttribute);
        attributeContainer.setSecondAttributeMap(percentageAttribute);
        attributeContainer.setComputeAttributeMap(computeAttribute);
        attributeContainer.setAddSecondAttributeMap(allAttribute);
        return attributeContainer;
    }

    /**
     * 1.将新加的属性加到对应的map中
     * 2.将一级属性转换为二级属性
     * 3.计算百分比属性
     */


    /**
     * 将属性添加到对应的属性map中，并如果是一级属性，则计算后加入到二级属性map
     *
     * @param attribute
     */
    public void putAndCompute(Attribute attribute) {
        if (attribute.getAttributeType().getAttrType() == AttributeType.MAX_HP.getAttrType()) {
            secondAttributeMap.get(attribute.getAttributeType()).addValue(attribute.getValue());
            addSecondAttributeMap.get(attribute.getAttributeType()).addValue(attribute.getValue());
            recompute(attribute.getAttributeType());
        }else{
            /**
             * 一级属性
             */
            if(attribute.getAttributeType().getAttrType()==AttributeType.PHYSICAL.getAttrType()) {
                changeAttribute(attribute);
            }else {
                /**
                 * 百分比属性
                 */
                recompute(attribute.getAttributeType());
            }
        }

    }

    /**
     * 将添加进来的一级属性属性转换为二级属性 并添加到对应的map中
     *
     * @param attribute
     */
    public void changeAttribute(Attribute attribute) {

        firstAttributeMap.get(attribute.getAttributeType()).addValue(attribute.getValue());
        Map<AttributeType, Attribute> attributeMap = attribute.getAttributeType().computeChangeAttribute(attribute.getValue());
        for (Attribute changeAttribute : attributeMap.values()) {
            secondAttributeMap.get(changeAttribute.getAttributeType()).addValue(changeAttribute.getValue());
            addSecondAttributeMap.get(changeAttribute.getAttributeType()).addValue(changeAttribute.getValue());
            recompute(changeAttribute.getAttributeType());
        }

    }

    /**
     * 根据属性类型重新计算某个二级属性加了百分比后的属性
     * @param attributeType
     *       传来的属性类型是百分比属性或二级属性
     */
    public void recompute(AttributeType attributeType) {
        if(attributeType.getAttrType()==AttributeType.ATTACK_SPEED_PERCENTAGE.getAttrType()){
            Attribute attribute = addSecondAttributeMap.get(AttributeType.valueOf(attributeType.getRelateType()));
            double endValue = attribute.getValue() * (1+ percentageAttributeMap.get(attributeType).getValue()/100.0);
            computeAttributeMap.put(attribute.getAttributeType(),Attribute.valueOf(attribute.getAttributeType(),(long)endValue));
        }else if(attributeType.getAttrType()==AttributeType.MAX_HP.getAttrType()){

            Attribute attribute = addSecondAttributeMap.get(attributeType);
            /**
             * 根据二级属性找到百分比属性
             */
            Attribute percentageAttribute = percentageAttributeMap.get(AttributeType.valueOf(attributeType.getRelateType()));
            double endValue = attribute.getValue() * (1+percentageAttribute.getValue()/100.0);
            computeAttributeMap.put(attribute.getAttributeType(),Attribute.valueOf(attribute.getAttributeType(),(long)endValue));
        }
    }

    /**
     * 添加玩家属性，并计算二级属性
     *
     * @param attrList
     */
    public void addAndComputeMap(List<Attribute> attrList) {

        for (Attribute attribute : attrList) {
            putAndCompute(attribute);
        }
    }
    /**
     * 将属性从map中移除，并如果是一级属性，则计算后再移除
     *
     * @param attribute
     */
    public void removeAndComputeAttribute(Attribute attribute) {
        if (attribute.getAttributeType().getAttrType() == AttributeType.MAX_HP.getAttrType()) {
            secondAttributeMap.get(attribute.getAttributeType()).reduce(attribute.getValue());
            addSecondAttributeMap.get(attribute.getAttributeType()).reduce(attribute.getValue());
            recompute(attribute.getAttributeType());
        }else{
            /**
             * 一级属性
             */
            if (attribute.getAttributeType().getAttrType() == 1) {
                changeReduceAttribute(attribute);
            }else {
                /**
                 * 百分比属性
                 */
                recompute(attribute.getAttributeType());
            }
        }
    }
    /**
     * 将移除的一级属性属性转换为二级属性 从map中移除
     *
     * @param attribute
     */
    public void changeReduceAttribute(Attribute attribute) {

        firstAttributeMap.get(attribute.getAttributeType()).reduce(attribute.getValue());
        Map<AttributeType, Attribute> attributeMap = attribute.getAttributeType().computeChangeAttribute(attribute.getValue());
        for (Attribute changeAttribute : attributeMap.values()) {
            secondAttributeMap.get(changeAttribute.getAttributeType()).reduce(changeAttribute.getValue());
            addSecondAttributeMap.get(changeAttribute.getAttributeType()).reduce(changeAttribute.getValue());
            recompute(changeAttribute.getAttributeType());
        }

    }
    /**
     * 移除玩家的属性，并计算二级属性
     *
     * @param attrList
     */
    public void removeAndCompute(List<Attribute> attrList) {
        for (Attribute attribute : attrList) {
            removeAndComputeAttribute(attribute);
        }
    }


    public Map<AttributeType, Attribute> getFirstAttributeMap() {
        return firstAttributeMap;
    }

    public void setFirstAttributeMap(Map<AttributeType, Attribute> firstAttributeMap) {
        this.firstAttributeMap = firstAttributeMap;
    }

    public Map<AttributeType, Attribute> getSecondAttributeMap() {
        return secondAttributeMap;
    }

    public void setSecondAttributeMap(Map<AttributeType, Attribute> secondAttributeMap) {
        this.secondAttributeMap = secondAttributeMap;
    }

    public Map<AttributeType, Attribute> getAddSecondAttributeMap() {
        return addSecondAttributeMap;
    }

    public void setAddSecondAttributeMap(Map<AttributeType, Attribute> addSecondAttributeMap) {
        this.addSecondAttributeMap = addSecondAttributeMap;
    }

    public Map<AttributeType, Attribute> getComputeAttributeMap() {
        return computeAttributeMap;
    }

    public void setComputeAttributeMap(Map<AttributeType, Attribute> computeAttributeMap) {
        this.computeAttributeMap = computeAttributeMap;
    }

    public Map<AttributeType, Attribute> getPercentageAttributeMap() {
        return percentageAttributeMap;
    }

    public void setPercentageAttributeMap(Map<AttributeType, Attribute> percentageAttributeMap) {
        this.percentageAttributeMap = percentageAttributeMap;
    }
}

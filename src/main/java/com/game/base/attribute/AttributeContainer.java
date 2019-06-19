package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;
import com.game.base.gameObject.model.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FIXME:注意每次计算属性后都要保存
 * @Author：xuxin
 * @Date: 2019/6/12 17:19
 */
public class AttributeContainer<T extends Creature> {
    private static Logger logger = LoggerFactory.getLogger(AttributeContainer.class);
    public AttributeContainer(){

    }
    public static AttributeContainer valueOf(){
        AttributeContainer attributeContainer = new AttributeContainer();
        Map<AttributeType, Attribute>  firstAttribute = new HashMap<>();
        for(AttributeType attributeType:AttributeType.values()){
            if(attributeType.getAttrType()==1) {
                firstAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
            }
        }
        attributeContainer.setFirstAttributeMap(firstAttribute);

        Map<AttributeType, Attribute>  secondAttribute = new HashMap<>();
        for(AttributeType attributeType:AttributeType.values()){
            if(attributeType.getAttrType()==2) {
                secondAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
            }
        }
        attributeContainer.setSecondAttributeMap(secondAttribute);

        Map<AttributeType, Attribute>  allAttribute = new HashMap<>();
        for(AttributeType attributeType:AttributeType.values()){
            if(attributeType.getAttrType()==2) {
                allAttribute.put(attributeType, Attribute.valueOf(attributeType, 0));
            }
        }
        attributeContainer.setAllAttributeMap(allAttribute);
        return attributeContainer;
    }
    // private Map<AttributeType, Attribute>  buffAttributeMap = new ConcurrentHashMap<>();
    /**
     * 一级属性
     */
    private Map<AttributeType, Attribute>  firstAttributeMap = new ConcurrentHashMap<>();

    /**
     * 二级属性
     *
     */
    private Map<AttributeType, Attribute> secondAttributeMap = new ConcurrentHashMap<>();

    /**
     * 累加计算后的二级属性
     */
    private Map<AttributeType, Attribute>  allAttributeMap = new ConcurrentHashMap<>();

    public void addAndComputeAttribute(Attribute attribute){
        if(attribute.getAttributeType().getAttrType()==1){
            if(firstAttributeMap.get(attribute.getAttributeType())!=null){
                firstAttributeMap.get(attribute.getAttributeType()).addValue(attribute.getValue());
                return;
            }
            firstAttributeMap.put(attribute.getAttributeType(), attribute);
            Map<AttributeType, Attribute> attributeMap = attribute.getAttributeType().computeChangeAttribute(attribute.getValue());
            addAttrMap(attributeMap,secondAttributeMap);
            return;
        }
        if(attribute.getAttributeType().getAttrType()==2) {
            if (secondAttributeMap.get(attribute.getAttributeType()) != null) {
                secondAttributeMap.get(attribute.getAttributeType()).addValue(attribute.getValue());
                return;
            }
            secondAttributeMap.put(attribute.getAttributeType(), attribute);
        }

    }

    /**
     * 添加玩家属性，并计算二级属性
     * @param attrMap
     */
    public void addAndCompute(Map<AttributeType, Attribute>  attrMap){
        for(Map.Entry<AttributeType, Attribute> entry:attrMap.entrySet()){
            addAndComputeAttribute(entry.getValue());
        }
    }
    public void removeAndComputeAttribute(Attribute attribute){
        if(attribute.getAttributeType().getAttrType()==1) {
            if (firstAttributeMap.get(attribute) != null) {
                firstAttributeMap.get(attribute.getAttributeType()).remove(attribute);
                Map<AttributeType, Attribute> attributeMap = attribute.getAttributeType().computeChangeAttribute(attribute.getValue());
                removeAttrMap(attributeMap, secondAttributeMap);
                return;
            }
            logger.warn("没有属性{}无法移除", attribute.getAttributeType());
            return;
        }
        if(attribute.getAttributeType().getAttrType()==2) {
            if (secondAttributeMap.get(attribute) != null) {
                secondAttributeMap.get(attribute.getAttributeType()).remove(attribute);
                return;
            }
            logger.warn("没有属性{}无法移除", attribute.getAttributeType());
            return;
        }

    }

    /**
     * 移除玩家的属性，并计算二级属性
     * @param attrMap
     */
    public void removeAndCompute(Map<AttributeType, Attribute> attrMap){

        for (Map.Entry<AttributeType, Attribute> entry : attrMap.entrySet()) {
            removeAndComputeAttribute(entry.getValue());
        }
    }

    /**
     * TODO: 以后用于玩家离线自动刷怪获得经验后，玩家登陆时重新计算玩家属性
     * 将一级属性计算结构累加后加到二级属性中
     */
    public void recompute(){
        /**
         * 将二级属性赋值到allAttributeMap中
         */
        allAttributeMap = new ConcurrentHashMap<>();
        addAttrMap(secondAttributeMap,allAttributeMap);
        /**
         * 将一级属性转换为二级属性后加到总属性中
         */
        for(Map.Entry<AttributeType, Attribute> entry :firstAttributeMap.entrySet()){
            Map<AttributeType, Attribute> attributeMap = entry.getKey().computeChangeAttribute(entry.getValue().getValue());
            addAttrMap(attributeMap,allAttributeMap);
        }
    }

    private void addAttrMap(Map<AttributeType, Attribute> attributeMap, Map<AttributeType, Attribute> endMap) {
        for(Map.Entry<AttributeType, Attribute> secondEntry:attributeMap.entrySet()){
            if(!endMap.containsKey(secondEntry.getKey())){
                endMap.put(secondEntry.getKey(),secondEntry.getValue());
            }else{
                Attribute attribute = endMap.get(secondEntry.getKey());
                attribute.addValue(secondEntry.getValue().getValue());
            }
        }
    }

    private void removeAttrMap(Map<AttributeType, Attribute> attributeMap, Map<AttributeType, Attribute> endMap) {
        for(Map.Entry<AttributeType, Attribute> entry:attributeMap.entrySet()){
            if(endMap.containsKey(entry.getKey())){
                endMap.get(entry.getKey()).remove(entry.getValue().getValue());
            }else{
                logger.error("玩家没有这类属性");
                return;
            }
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

    public Map<AttributeType, Attribute> getAllAttributeMap() {
        return allAttributeMap;
    }

    public void setAllAttributeMap(Map<AttributeType, Attribute> allAttributeMap) {
        this.allAttributeMap = allAttributeMap;
    }
}

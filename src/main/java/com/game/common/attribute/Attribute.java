package com.game.common.attribute;


import com.game.common.attribute.constant.AttributeType;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 15:05
 */
public class Attribute {
    /**
     * 属性类型
     */
    private AttributeType attributeType;
    /**
     * 属性值
     */
    private long value;


    public static Attribute valueOf(AttributeType type , long value){
        Attribute attribute = new Attribute();
        attribute.setAttributeType(type);
        attribute.setValue(value);
        return attribute;
    }
    public void remove(Attribute attribute){
        if(this.value-attribute.getValue()>=0) {
            this.value -= attribute.getValue();
        }
    }
    public void reduce(long value){
        this.value-=value;
    }
    public void addValue(long value){
        this.value+=value;
    }
    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
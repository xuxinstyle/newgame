package com.game.base.attribute;


import com.game.base.attribute.constant.AttributeType;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 15:05
 */
public class Attribute {
    /**
     * 属性类型
     */
    protected AttributeType attributeType;
    /**
     * 属性值
     */
    protected long value;


    public static Attribute valueOf(AttributeType type , long value){
        Attribute attribute = new Attribute();
        attribute.setAttributeType(type);
        attribute.setValue(value);
        return attribute;
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

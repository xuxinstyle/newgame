package com.game.base.attribute;


import com.game.base.attribute.constant.AttributeType;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 15:05
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class Attribute {
    /**
     * 属性类型
     */
    public AttributeType type;
    /**
     * 属性值
     */
    public long value;


    public static Attribute valueOf(AttributeType type , long value){
        Attribute attribute = new Attribute();
        attribute.setType(type);
        attribute.setValue(value);
        return attribute;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

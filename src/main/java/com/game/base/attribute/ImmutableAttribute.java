package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;

/**
 * @Author：xuxin
 * @Date: 2019/7/1 14:08
 */
public class ImmutableAttribute extends Attribute{

    public static ImmutableAttribute wrapper(Attribute attribute){
        ImmutableAttribute immutableAttribute = new ImmutableAttribute();
        immutableAttribute.value = attribute.getValue();
        immutableAttribute.attributeType = attribute.getAttributeType();
        return immutableAttribute;
    }


    @Override
    public AttributeType getAttributeType() {
        return attributeType;
    }

    @Override
    public void setAttributeType(AttributeType attributeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        throw new UnsupportedOperationException();
    }
}

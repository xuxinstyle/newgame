package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;

/**
 * FIXME::由于该属性无法存进数据库，所以暂时用不上，等到完善了缓存模型之后再使用
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

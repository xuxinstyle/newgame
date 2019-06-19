package com.game.base.gameObject.model;

import com.game.base.attribute.AttributeContainer;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 17:12
 */
public abstract class Creature<T extends Creature> extends VisibleObject {

    protected AttributeContainer<T> attributeContainer;

    public AttributeContainer<T> getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(AttributeContainer<T> attributeContainer) {
        this.attributeContainer = attributeContainer;
    }

}

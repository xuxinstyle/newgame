package com.game.base.gameobject.model;

import com.game.base.attribute.AttributeContainer;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 17:12
 */
public abstract class Creature<T extends Creature> extends VisibleObject {
    /**
     * 属性容器
     */
    protected AttributeContainer<T> attributeContainer;

    /**
     * TODO:buff容器
     */



    public AttributeContainer<T> getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(AttributeContainer<T> attributeContainer) {
        this.attributeContainer = attributeContainer;
    }

}

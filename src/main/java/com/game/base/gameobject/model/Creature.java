package com.game.base.gameobject.model;

import com.game.base.attribute.AttributeContainer;
import com.game.base.attribute.CreatureAttributeContainer;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 17:12
 */
public abstract class Creature<T extends Creature> extends VisibleObject {
    /**
     * 属性容器
     */
    protected CreatureAttributeContainer<T> attributeContainer;

    /**
     * TODO:buff容器
     */



    public CreatureAttributeContainer<T> getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(CreatureAttributeContainer<T> attributeContainer) {
        this.attributeContainer = attributeContainer;
    }

}

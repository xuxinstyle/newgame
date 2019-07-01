package com.game.base.gameobject.model;

import com.game.base.attribute.container.CreatureAttributeContainer;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 17:12
 */
public abstract class Creature<T extends Creature> extends VisibleObject {
    /**
     * 属性容器
     */
    protected CreatureAttributeContainer attributeContainer;

    /**
     * TODO:buff容器
     */



    public CreatureAttributeContainer getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(CreatureAttributeContainer attributeContainer) {
        this.attributeContainer = attributeContainer;
    }

}

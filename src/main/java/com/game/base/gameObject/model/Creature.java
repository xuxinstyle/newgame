package com.game.base.gameObject.model;

import com.game.base.attribute.CreatureAttributeContainer;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 17:12
 */
public abstract class Creature<T extends Creature> extends VisibleObject {

    protected CreatureAttributeContainer<T> attributeContainer;
}

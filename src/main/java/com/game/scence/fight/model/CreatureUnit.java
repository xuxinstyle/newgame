package com.game.scence.fight.model;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.visible.model.Position;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 12:16
 */
public abstract class CreatureUnit extends BaseUnit{


    /**
     * 属性
     */
    CreatureAttributeContainer attributeContainer;
    /**
     * 当前血量
     */
    private long currHp;
    /**
     * 当前蓝量
     */
    private long currMp;

    public long getCurrHp() {
        return currHp;
    }

    public void setCurrHp(long currHp) {
        this.currHp = currHp;
    }

    public long getCurrMp() {
        return currMp;
    }

    public void setCurrMp(long currMp) {
        this.currMp = currMp;
    }

    public CreatureAttributeContainer getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(CreatureAttributeContainer attributeContainer) {
        this.attributeContainer = attributeContainer;
    }
}

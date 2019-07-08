package com.game.scence.fight.model;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeIdEnum;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.monster.resource.MonsterResource;
import com.game.scence.visible.model.Position;

import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 12:19
 */
public class MonsterUnit extends CreatureUnit {
    /**
     * 怪物对应的配置表
     */
    private MonsterResource monsterResource;
    /**
     * 存活状态 true：存活 false：死亡
     */
    private boolean islive;

    /**
     * 生成怪物战斗单元
     * 生成属性
     * @param monsterResource
     * @return
     */
    public static MonsterUnit valueOf(MonsterResource monsterResource){
        MonsterUnit monsterUnit = new MonsterUnit();
        monsterUnit.setMonsterResource(monsterResource);
        List<Attribute> attrs = monsterResource.getAttrs();
        monsterUnit.setAttributeContainer(new CreatureAttributeContainer());
        CreatureAttributeContainer attributeContainer = monsterUnit.getAttributeContainer();
        attributeContainer.putAndComputeAttributes(AttributeIdEnum.BASE,attrs);
        Attribute maxHp = attributeContainer.getFinalAttributes().get(AttributeType.MAX_HP);
        Attribute maxMp = attributeContainer.getFinalAttributes().get(AttributeType.MAX_MP);
        /**
         * 这里需要配置表配最大血量的属性，配置表已经配了最大血量了，肯定不为空
         */
        monsterUnit.setCurrHp(maxHp.getValue());
        monsterUnit.setCurrMp(maxMp.getValue());
        monsterUnit.setVisibleName(monsterResource.getName());
        monsterUnit.setType(ObjectType.MONSTER);
        monsterUnit.setIslive(true);
        monsterUnit.setPosition(Position.valueOf(monsterResource.getPx(),monsterResource.getPy()));
        return monsterUnit;
    }

    public boolean isIslive() {
        return islive;
    }

    public void setIslive(boolean islive) {
        this.islive = islive;
    }

    public MonsterResource getMonsterResource() {
        return monsterResource;
    }

    public void setMonsterResource(MonsterResource monsterResource) {
        this.monsterResource = monsterResource;
    }
}

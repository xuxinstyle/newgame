package com.game.scence.field.packet;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.visible.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/5 22:58
 */
public class SM_ShowMonsterInfo {
    /**
     * 怪物唯一标识id
     */
    private long monsterObjectId;
    /**
     * 怪物名称
     */
    private String name;
    /**
     * 怪物位置
     */
    private Position position;
    /**
     * 怪物属性
     */
    private List<Attribute> attributeList;

    /**
     * 怪物当前血量和蓝量
     */
    private long currHp;
    private long currMp;

    public static SM_ShowMonsterInfo valueOf(MonsterUnit monsterUnit){
        SM_ShowMonsterInfo sm = new SM_ShowMonsterInfo();
        sm.setName(monsterUnit.getVisibleName());
        sm.setPosition(monsterUnit.getPosition());
        sm.setMonsterObjectId(monsterUnit.getId());
        CreatureAttributeContainer attributeContainer = monsterUnit.getAttributeContainer();
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        List<Attribute> attributeList = new ArrayList<>(finalAttributes.values());
        sm.setAttributeList(attributeList);
        sm.setCurrHp(monsterUnit.getCurrHp());
        sm.setCurrMp(monsterUnit.getCurrMp());
        return sm;
    }

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

    public long getMonsterObjectId() {
        return monsterObjectId;
    }

    public void setMonsterObjectId(long monsterObjectId) {
        this.monsterObjectId = monsterObjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}

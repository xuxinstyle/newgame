package com.game.scence.fight.constant;

import com.game.base.attribute.constant.AttributeType;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 22:33
 */
public enum  HurtType {
    /**
     * 魔法伤害
     */
    MAGIC(1){
        @Override
        public AttributeType getHurtAttributeType() {
            return AttributeType.PHYSICAL_ATTACK;
        }

        @Override
        public AttributeType getDefenceAttributeType() {
            return AttributeType.MAGIC_DEFENSE;
        }
    },
    /**
     * 物理伤害
     */
    PHYSICAL(2){
        @Override
        public AttributeType getHurtAttributeType() {
            return AttributeType.MAGIC_ATTACK;
        }

        @Override
        public AttributeType getDefenceAttributeType() {
            return AttributeType.PHYSICAL_DEFENSE;
        }
    },
    ;

    /**
     * 获取影响伤害的属性
     * @return
     */
    public abstract AttributeType getHurtAttributeType();

    /**
     * 获取防御属性
     * @return
     */
    public abstract AttributeType getDefenceAttributeType();

    HurtType(int id){
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

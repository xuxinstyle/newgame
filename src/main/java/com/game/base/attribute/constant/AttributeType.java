package com.game.base.attribute.constant;

import com.game.base.attribute.Attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 15:00
 */
public enum AttributeType {


    /**
     * ------------------------------------ -  二级属性   ------------------------------
     */
    /**
     * 物理攻击
     */
    PHYSICAL_ATTACK(1,"物理攻击",AttributeKind.SECOND_ATTRIBUTE){
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{PHYSICAL_ATTACK_PERCENTAGE};
        }

        @Override
        public AttributeType[] getFirstAttributes() {
            return new AttributeType[]{POWER};
        }
    },
    /**
     * 法术攻击  实际伤害=A/(1+B/100)  A是攻击力或法强伤害 B是护甲 或魔抗
     */
    MAGIC_ATTACK(2,"法术攻击",AttributeKind.SECOND_ATTRIBUTE){
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAGIC_ATTACK_PERCENTAGE};
        }

        @Override
        public AttributeType[] getFirstAttributes() {
            return new AttributeType[]{INTELLIGENCE};
        }

    },

    /**
     * 物理防御    y*1/x x为防御属性值 y为对手的攻击力或者本应该照成的血量减少值
     *   减伤百分比＝x/(x＋602)  x为防御值
     */
    PHYSICAL_DEFENSE(3,"物理防御",AttributeKind.SECOND_ATTRIBUTE){
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{PHYSICAL_DEFENSE_PERCENTAGE};
        }

        @Override
        public AttributeType[] getFirstAttributes() {
            return new AttributeType[]{PHYSICAL};
        }

    },
    /**
     * 法术防御
     * 减伤百分比＝x/(x＋602)  x为防御值
     */
    MAGIC_DEFENSE(4,"法术防御",AttributeKind.SECOND_ATTRIBUTE){
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAGIC_DEFENSE_PERCENTAGE};
        }

    },
    /**
     * 最大血量
     */
    MAX_HP(5,"最大血量",AttributeKind.SECOND_ATTRIBUTE){
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_HP_PERCENTAGE};
        }

        @Override
        public AttributeType[] getFirstAttributes() {
            return new AttributeType[]{ PHYSICAL,POWER};
        }

    },
    /**
     * 最大蓝量
     */
    MAX_MP(6,"最大蓝量",AttributeKind.SECOND_ATTRIBUTE){
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_MP_PERCENTAGE};
        }

        @Override
        public AttributeType[] getFirstAttributes() {
            return new AttributeType[]{INTELLIGENCE,AGILE};
        }
    },
    /**
     * 物理穿透率 对方防御= 原始防御*（1-%X） x为物理穿透率
     */
    PHYSICAL_PENETRATION(13,"物理穿透率",AttributeKind.SECOND_ATTRIBUTE),
    /**
     * 法术穿透率
     */
    SPEEL_PENETRATION(14,"法术穿透率",AttributeKind.SECOND_ATTRIBUTE),

    /**
     * -------------------------------------- 一级属性 ----------------------------
     */
    /**
     * 力量  +100 HP 1 物理攻击
     */
    POWER(8,"力量",AttributeKind.FIRST_ATTRIBUTE){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_HP,Attribute.valueOf(MAX_HP,value*100));
            attributeMap.put(PHYSICAL_ATTACK, Attribute.valueOf(PHYSICAL_ATTACK, value));
            return attributeMap;
        }

        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_HP, PHYSICAL_ATTACK};
        }
    },
    /**
     * 智力  +100 MP 1 法术攻击
     */
    INTELLIGENCE(9,"智力",AttributeKind.FIRST_ATTRIBUTE){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_MP,Attribute.valueOf(MAX_MP,value*100));
            attributeMap.put(MAGIC_ATTACK, Attribute.valueOf(MAGIC_ATTACK, value));
            return attributeMap;
        }

        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_MP, MAGIC_ATTACK};
        }
    },
    /**
     * 体力  +100 HP 1 物理防御
     */
    PHYSICAL(10,"体力",AttributeKind.FIRST_ATTRIBUTE){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_HP,Attribute.valueOf(MAX_HP,value*100));
            attributeMap.put(MAGIC_DEFENSE, Attribute.valueOf(MAGIC_DEFENSE, value));
            return attributeMap;
        }

        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_HP,PHYSICAL_DEFENSE};
        }
    },
    /**
     * 敏捷   +100 MP 1 攻速
     */
    AGILE(11,"敏捷",AttributeKind.FIRST_ATTRIBUTE){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_MP,Attribute.valueOf(MAX_MP,value*100));
            return attributeMap;
        }

        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_MP};
        }
    },


    /**
     *  -----------------------------------  百分比属性
     */

    /**
     * 增加最大血量的百分比
     */
    MAX_HP_PERCENTAGE(15,"增加最大血量的百分比",AttributeKind.OTHER_ATTRIBUTE){
        @Override
        public AttributeType[] getcalculateAttributes() {
            return new AttributeType[]{MAX_HP};
        }
    },
    /**
     * 增加最大蓝量百分比
     */
    MAX_MP_PERCENTAGE(16,"增加最大蓝量的百分比",AttributeKind.OTHER_ATTRIBUTE){
        @Override
        public AttributeType[] getcalculateAttributes() {
            return new AttributeType[]{MAX_MP};
        }
    },
    /**
     * 增加物理防御的百分比
     */
    PHYSICAL_DEFENSE_PERCENTAGE(17,"增加物理防御的百分比",AttributeKind.OTHER_ATTRIBUTE){
        @Override
        public AttributeType[] getcalculateAttributes() {
            return new AttributeType[]{PHYSICAL_DEFENSE};
        }
    },
    /**
     * 增加法术防御的百分比
     */
    MAGIC_DEFENSE_PERCENTAGE(18,"增加法术防御的百分比",AttributeKind.OTHER_ATTRIBUTE){
        @Override
        public AttributeType[] getcalculateAttributes() {
            return new AttributeType[]{MAGIC_DEFENSE};
        }
    },
    /**
     * 增加物理攻击的百分比
     */
    PHYSICAL_ATTACK_PERCENTAGE(19,"增加物理攻击百分比",AttributeKind.OTHER_ATTRIBUTE){
        @Override
        public AttributeType[] getcalculateAttributes() {
            return new AttributeType[]{PHYSICAL_ATTACK};
        }
    },
    /**
     * 增加法术攻击的百分比(20,"增加法术攻击的百分比)
     */
    MAGIC_ATTACK_PERCENTAGE(20,"增加法术攻击的百分比",AttributeKind.OTHER_ATTRIBUTE){
        @Override
        public AttributeType[] getcalculateAttributes() {
            return new AttributeType[]{MAGIC_ATTACK};
        }
    },
    ;

    private int id;

    private String attrName;

    private AttributeKind kind;


    /**
     * 用于获取影响对应二级属性的其他属性集合
     * @return
     */
    public AttributeType[] getEffectAttributes(){
        return null;
    }

    /**
     * 获取影响二级属性的一级属性
     * @return
     */
    public AttributeType[] getFirstAttributes(){
        return null;
    }


    /**
     * 获取被百分比影响的其他属性
     */
    public AttributeType[] getcalculateAttributes(){
        return null;
    }
    /**
     * TODO:找累加计算的属性集合
     */

    /**
     * @param value
     * @return
     */
    public Map<AttributeType, Attribute> computeChangeAttribute(long value){

        return null;
    }

    AttributeType(int id, String attrName,AttributeKind kind){
        this.id = id;
        this.attrName = attrName;
        this.kind = kind;
    }


    public int getId() {
        return id;
    }



    public String getAttrName() {
        return attrName;
    }


    public AttributeKind getKind() {
        return kind;
    }


}

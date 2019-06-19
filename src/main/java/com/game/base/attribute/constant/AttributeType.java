package com.game.base.attribute.constant;

import com.game.base.attribute.Attribute;

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
    PHYSICAL_ATTACK(1,"物理攻击",2),
    /**
     * 法术攻击
     */
    MAGIC_ATTACK(2,"法术攻击",2),

    /**
     * 物理防御    y*1/x x为防御属性值 y为对手的攻击力或者本应该照成的血量减少值
     */
    PHYSICAL_DEFENSE(3,"物理防御",2),
    /**
     * 法术防御
     */
    MAGIC_DEFENSE(4,"法术防御",2),
    /**
     * 最大血量
     */
    MAX_HP(5,"最大血量",2),
    /**
     * 最大蓝量
     */
    MAX_MP(6,"最大蓝量",2),
    /**
     * 攻速 在表中存的是整数x， 普通攻击一次的时间间隔s   = (2+0.5x)/x
     */
    ATTACK_SPEED(7,"攻速",2),

    /**
     * -------------------------------------- 一级属性 ----------------------------
     */
    /**
     * 力量  +100 HP 1 物理攻击
     */
    POWER(8,"力量",1){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_HP,Attribute.valueOf(MAX_HP,value*100));
            attributeMap.put(PHYSICAL_ATTACK, Attribute.valueOf(PHYSICAL_ATTACK, value));
            return attributeMap;
        }
    },
    /**
     * 智力  +100 MP 1 法术攻击
     */
    INTELLIGENCE(9,"智力",1){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_MP,Attribute.valueOf(MAX_MP,value*100));
            attributeMap.put(MAGIC_ATTACK, Attribute.valueOf(MAGIC_ATTACK, value));
            return attributeMap;
        }
    },
    /**
     * 体力  +100 HP 1 物理防御
     */
    PHYSICAL(10,"体力",1){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_HP,Attribute.valueOf(MAX_HP,value*100));
            attributeMap.put(MAGIC_DEFENSE, Attribute.valueOf(MAGIC_DEFENSE, value));
            return attributeMap;
        }
    },
    /**
     * 敏捷   +100 MP 1 攻速
     */
    AGILE(11,"敏捷",1){
        @Override
        public Map<AttributeType, Attribute> computeChangeAttribute(long value) {
            Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
            attributeMap.put(MAX_MP,Attribute.valueOf(MAX_MP,value*100));
            attributeMap.put(ATTACK_SPEED, Attribute.valueOf(ATTACK_SPEED, value));
            return attributeMap;
        }
    },
    ;
    private int id;

    private String attrName;
    /**
     * 属性类型：一级属性还是二级属性
     */
    private int attrType;

    public Map<AttributeType, Attribute> computeChangeAttribute(long value){
        Map<AttributeType, Attribute> attributeMap = new ConcurrentHashMap<>();
        attributeMap.put(this,Attribute.valueOf(this,value));
        return attributeMap;
    }

    AttributeType(int id, String attrName, int attrType){
        this.id = id;
        this.attrName = attrName;
        this.attrType = attrType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getAttrType() {
        return attrType;
    }

    public void setAttrType(int attrType) {
        this.attrType = attrType;
    }
}

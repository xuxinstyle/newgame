package com.game.role.equip.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 10:36
 */
public enum EquipType {
    /**
     * 武器  + 力量/智力 + 攻击力 + 攻速/敏捷
     */
    WEAPON(1),
    /**
     * 衣服 + hp + 防御
     */
    CLOTHES(2),
    /**
     * 头盔  + hp + 防御
     */
    HAT(3),
    /**
     * 腰带  + 蓝量 + 防御
     */
    BELT(4),
    /**
     * 鞋子  + 攻速 +蓝量
     */
    SHOES(5),
    /**
     * 项链  +智力 +蓝量
     */
    NECKLACE(6),
    /**
     * 戒子   智力 +蓝量
     */
    RING(7),
    /**
     * 护腕   防御+ 力量
     */
    GLOVE(8),
    ;
    /**
     * 位置id
     */
    private int position;

    static Map<Integer,EquipType> positionMap = new HashMap<>();

    static {
        for (EquipType equipType: values()){
            positionMap.put(equipType.getPosition(),equipType);
        }
    }

    public static EquipType getEquipType(int position){
        for(EquipType equipType:values()){
            if(equipType.position==position){
                return equipType;
            }
        }
        return null;
    }
    EquipType(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}

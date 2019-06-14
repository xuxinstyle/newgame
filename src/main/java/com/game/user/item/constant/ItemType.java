package com.game.user.item.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 18:27
 */
public enum  ItemType {
    /** 货币*/
    CURRENCY(1),

    /** 装备*/
    EQUIPMENT(2),
    /** 可镶嵌到装备的宝石*/
    GEM(3),
    /** 经验*/
    EXP(4),
    /** 强化石*/
    FORTIFIEDSTONE(5),
    /**升阶石*/
    UPSTONE(6),
    ;
    /** 道具类型Id*/
    private int id;

    ItemType(int id){
        this.id = id;
    }

}

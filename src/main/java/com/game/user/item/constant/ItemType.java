package com.game.user.item.constant;

import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;
import com.game.user.item.model.Exp;
import com.game.user.item.model.Medicine;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 18:27
 */
public enum  ItemType {

    /** 装备*/
    EQUIPMENT(2,Equipment.class),
    /** 经验*/
    EXP(4, Exp.class),
    /** 强化石*/
    FORTIFIEDSTONE(5),
    /**升阶石*/
    UPSTONE(6),
    /** 药品*/
    MEDICINE(7,Medicine.class),

    ;
    /** 道具类型Id*/
    private int id;
    /** 道具实例类*/
    private Class<? extends AbstractItem> itemClazz;

    ItemType(int id){
        this.id = id;
    }
    ItemType(int id, Class<? extends AbstractItem> itemClazz){
        this.id = id;
        this.itemClazz = itemClazz;
    }
    public AbstractItem create(){
        try{
            return itemClazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("生成道具实例["+itemClazz.getName()+"]错误");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

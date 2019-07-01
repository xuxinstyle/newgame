package com.game.role.equip.model;

import com.game.user.item.model.Equipment;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 17:18
 */
public class EquipHole {
    /**
     * 装备位置
     */
    private int position;
    /**
     * 装备
     */
    private Equipment equipment;

    public static EquipHole valueOf(int position){
        EquipHole equipHole = new EquipHole();
        equipHole.setPosition(position);
        return equipHole;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}

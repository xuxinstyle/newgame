package com.game.user.equip.model;

import com.game.user.item.model.Equipment;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 17:18
 */
public class EquipHole {
    private int position;
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

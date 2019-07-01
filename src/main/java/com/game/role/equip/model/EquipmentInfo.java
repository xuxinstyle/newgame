package com.game.role.equip.model;

import com.game.role.equip.constant.EquipType;
import com.game.user.item.model.Equipment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家的装备信息
 *
 * @Author：xuxin
 * @Date: 2019/6/16 18:32
 */
public class EquipmentInfo {
    /**
     * 装备信息map
     */
    private Map<EquipType, EquipHole> equipmentMap;

    public static EquipmentInfo valueOf(){
        EquipmentInfo equipmentInfo = new EquipmentInfo();
        Map<EquipType, EquipHole> equipmentMap = new HashMap<>();
        for (EquipType equipType:EquipType.values()){
            EquipHole equipHole = EquipHole.valueOf(equipType.getPosition());
            equipmentMap.put(equipType, equipHole);
        }
        equipmentInfo.setEquipmentMap(equipmentMap);
        return equipmentInfo;
    }
    public Equipment wearEquipment(EquipType equipType, Equipment equipment) {
        EquipHole equipHole = equipmentMap.get(equipType);
        Equipment oldEquipment = equipHole.getEquipment();
        equipHole.setEquipment(equipment);
        return oldEquipment;
    }

    public Equipment unEquip(EquipType equipType) {
        Equipment equipment = equipmentMap.get(equipType).getEquipment();
        if(equipment==null){
            return null;
        }
        EquipHole equipHole = equipmentMap.get(equipType);
        equipHole.setEquipment(null);
        return equipment;
    }

    public Equipment getPositionEquip(EquipType equipType) {
        return equipmentMap.get(equipType).getEquipment();
    }

    public Map<EquipType, EquipHole> getEquipmentMap() {
        return equipmentMap;
    }

    public void setEquipmentMap(Map<EquipType, EquipHole> equipmentMap) {
        this.equipmentMap = equipmentMap;
    }
}

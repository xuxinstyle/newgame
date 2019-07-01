package com.game.base.attribute.attributeid;

import com.game.role.equip.constant.EquipType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/26 12:19
 */
public class EquipAttributeId implements AttributeId {
    private static final Map<Integer,EquipAttributeId> typeToAttributeId =
            new HashMap<>(EquipType.values().length);
    static {
        for(EquipType type:EquipType.values()){
            typeToAttributeId.put(type.getPosition(),new EquipAttributeId(type));
        }
    }
    private final EquipType type;

    public EquipAttributeId(EquipType type) {
        this.type = type;
    }

    public static EquipAttributeId getAttributeId(int equipType){
        return typeToAttributeId.get(equipType);
    }
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return "EquipAttributeId{position="+type+"}";
    }
}

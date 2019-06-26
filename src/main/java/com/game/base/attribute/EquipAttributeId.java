package com.game.base.attribute;

import com.game.user.equip.constant.EquipType;

import java.io.Serializable;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result+((type==null)? 0:type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        EquipAttributeId that = (EquipAttributeId) o;
        return type == that.type;
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

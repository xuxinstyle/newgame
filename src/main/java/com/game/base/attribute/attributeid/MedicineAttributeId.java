package com.game.base.attribute.attributeid;

import com.game.user.item.constant.MedicineType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/26 14:16
 */
public class MedicineAttributeId implements AttributeId  {

    private static final Map<Integer,MedicineAttributeId> medicineToAttributeId =
            new HashMap<>(MedicineType.values().length);

    static {
        for(MedicineType type:MedicineType.values()){
            medicineToAttributeId.put(type.getItemModelId(),new MedicineAttributeId(type.getItemModelId()));
        }
    }
    public static MedicineAttributeId getMedicineAttributeId(int itemModel){
        return medicineToAttributeId.get(itemModel);
    }
    /**
     * 道具唯一id
     */
    private int itemModelId;

    public MedicineAttributeId(int itemModelId){
        this.itemModelId = itemModelId;
    }
    @Override
    public String getName() {
        return "MedicineAttributeId{itemModelId="+itemModelId+"}";
    }

    @Override
    public String toString() {
        return getName();
    }

    public static Map<Integer, MedicineAttributeId> getMedicineToAttributeId() {
        return medicineToAttributeId;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
}

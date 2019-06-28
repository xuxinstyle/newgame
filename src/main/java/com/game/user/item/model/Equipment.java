package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.user.equip.constant.EquipType;
import com.game.user.equip.resource.EquipResource;
import com.game.user.item.resource.ItemResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 11:23
 */
public class Equipment extends AbstractItem {
    /**
     * 装备位置
     */
    private EquipType equipType;
    /**
     * 基础属性加成
     */
    private List<Attribute> attributeList;
    /**
     * 强化属性加成
     */
    private Map<AttributeType, Attribute> strenAttributeMap;
    /**
     * 品质
     */
    private int quality;
    /**
     * 强化等级
     */
    private int strenNum;

    @Override
    public Equipment copy() {
        Equipment equipment = new Equipment();
        equipment.objectId = createItemObjectId();
        equipment.itemModelId = this.getItemModelId();
        equipment.num = this.getNum();
        equipment.status = this.getStatus();
        equipment.deprecatedTime = this.getDeprecatedTime();
        equipment.attributeList = this.getAttributeList();
        equipment.equipType = this.getEquipType();
        equipment.strenNum = this.strenNum;
        equipment.itemType = this.itemType;
        equipment.quality = this.quality;
        equipment.strenAttributeMap = this.strenAttributeMap;
        return equipment;
    }

    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);
        EquipResource equipResource = SpringContext.getItemService().getEquipResource(itemModelId);
        this.equipType = EquipType.valueOf(equipResource.getEquipType());
        this.strenNum = 0;
        this.quality = itemResource.getQuality();
        this.attributeList = equipResource.getBaseAttributeList();
        this.strenAttributeMap = new HashMap<>();
    }

    /**
     * 强化装备
     * @return
     */
    public boolean strenEquip() {

        EquipResource equipResource = SpringContext.getItemService().getEquipResource(itemModelId);
        if (strenNum >= equipResource.getMaxLevel()) {
            return false;
        }
        this.strenNum++;

        List<Attribute> upAttributeList = equipResource.getUpAttributeList();
        for (Attribute attribute : upAttributeList) {
            if (this.strenAttributeMap.get(attribute.getAttributeType()) == null) {
                this.strenAttributeMap.put(attribute.getAttributeType(), attribute);
            } else {
                this.strenAttributeMap.get(attribute.getAttributeType()).addValue(attribute.getValue());
            }
        }
        return true;
    }

    public Map<AttributeType, Attribute> getStrenAttributeMap() {
        return strenAttributeMap;
    }

    public void setStrenAttributeMap(Map<AttributeType, Attribute> strenAttributeMap) {
        this.strenAttributeMap = strenAttributeMap;
    }

    public EquipType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public int getStrenNum() {
        return strenNum;
    }

    public void setStrenNum(int strenNum) {
        this.strenNum = strenNum;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}

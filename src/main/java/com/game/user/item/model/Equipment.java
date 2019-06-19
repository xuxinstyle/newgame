package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.user.equip.constant.EquipType;
import com.game.user.equip.resource.EquipResource;
import com.game.user.item.resource.ItemResource;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 11:23
 */
public class Equipment extends AbstractItem {

    private EquipType equipType;
    /** 属性加成*/
    private Map<AttributeType, Attribute> attributeMap;
    private int quality;
    private int level;

    @Override
    public Equipment copy(){
        Equipment equipment = new Equipment();
        equipment.objectId = createItemObjectId();
        equipment.itemModelId = this.getItemModelId();
        equipment.num = this.getNum();
        equipment.status = this.getStatus();
        equipment.deprecatedTime = this.getDeprecatedTime();
        equipment.attributeMap = this.getAttributeMap();
        equipment.equipType = this.getEquipType();
        equipment.level = this.level;
        equipment.itemType = this.itemType;
        equipment.quality = this.quality;
        return equipment;
    }

    @Override
    public void init(ItemResource itemResource, Map<String, Object> params){
        super.init(itemResource,  params);
        EquipResource equipResource = SpringContext.getItemService().getEquipResource(itemModelId);
        this.attributeMap = equipResource.getBaseAttributeMap();
        this.equipType = EquipType.valueOf(equipResource.getEquipType());
        this.level = 1;
        this.quality = itemResource.getQuality();
    }
    public EquipType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
    }

    public Map<AttributeType, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}

package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.role.equip.constant.EquipType;
import com.game.role.equip.resource.EquipResource;
import com.game.role.equipstren.resource.EquipStrenResource;
import com.game.user.item.resource.ItemResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<Attribute> baseAttributeList;
    /**
     * 强化属性加成
     */
    private List<Attribute> strenAttributeList;
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
        equipment.baseAttributeList = this.getBaseAttributeList();
        equipment.equipType = this.getEquipType();
        equipment.strenNum = this.strenNum;
        equipment.itemType = this.itemType;
        equipment.quality = this.quality;
        equipment.strenAttributeList = this.strenAttributeList;
        return equipment;
    }

    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);
        EquipResource equipResource = SpringContext.getItemService().getEquipResource(itemModelId);
        this.equipType = EquipType.valueOf(equipResource.getEquipType());
        this.strenNum = 0;
        this.quality = itemResource.getQuality();
        this.baseAttributeList = equipResource.getBaseAttributeList();
        this.strenAttributeList = new ArrayList<>();
    }

    /**
     * 强化装备
     * @return
     */
    public boolean doStrenEquip() {
        EquipStrenResource equipStrenResource = SpringContext.getEquipStrenService().
                getEquipStrenResource(equipType.getPosition(), quality, strenNum+1);
        if (equipStrenResource==null) {
            return false;
        }
        this.strenNum++;

        strenAttributeList = equipStrenResource.getAttributeList();

        return true;
    }

    public List<Attribute> getStrenAttributeList() {
        return strenAttributeList;
    }

    public void setStrenAttributeList(List<Attribute> strenAttributeList) {
        this.strenAttributeList = strenAttributeList;
    }

    public EquipType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
    }

    public List<Attribute> getBaseAttributeList() {
        return baseAttributeList;
    }

    public void setBaseAttributeList(List<Attribute> baseAttributeList) {
        this.baseAttributeList = baseAttributeList;
    }

    public List<Attribute> getAllAttributes(){
        List<Attribute> allAttributeList = new ArrayList<>();
        allAttributeList.addAll(baseAttributeList);
        allAttributeList.addAll(strenAttributeList);
        return allAttributeList;
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

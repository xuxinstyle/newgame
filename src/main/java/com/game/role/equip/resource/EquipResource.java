package com.game.role.equip.resource;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.user.condition.model.EquipCondition;
import com.game.user.condition.model.StrenCondition;
import com.game.user.condition.model.UpgradeCondition;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:这里在增加缓存之后再增加不可变属性
 * @Author：xuxin
 * @Date: 2019/6/16 17:33
 */
@LoadResource
public class EquipResource {
    /**
     * 道具表id
     */
    private int itemId;
    /**
     * 装备名
     */
    private String equipName;
    /**
     * 装备类型
     */
    private String equipType;
    /**
     * 升级后对应的道具表id
     */
    private int upgradeId;
    /**
     * 最大强化等级
     */
    private int maxLevel;

    /**
     * 穿戴条件
     */
    @Analyze("analyzeEquipCondition")
    private String equipConditions;

    private EquipCondition equipCondition;
    /**
     * 强化条件
     */
    @Analyze("analyzeStreCondition")
    private String streConditions;

    private StrenCondition streCondition;
    /**
     * 升阶条件
     */
    @Analyze("analyzeUpgradeCondition")
    private String upgradeConditions;

    private UpgradeCondition upgradeCondition;
    /**
     * 装备基础属性
     */
    @Analyze("analyzeBaseAttribute")
    private String baseAttribute;

    private List<Attribute> baseAttributeList;

    public void analyzeEquipCondition() {
        String[] split = this.equipConditions.split("_");
        EquipCondition condition = new EquipCondition();
        condition.setCareer(Integer.parseInt(split[0]));
        condition.setPlayerNeedLevel(Integer.parseInt(split[1]));
        this.equipCondition = condition;
    }

    public void analyzeStreCondition() {
        String[] split = this.streConditions.split("_");
        StrenCondition condition = new StrenCondition();
        condition.setItemModelId(Integer.parseInt(split[0]));
        condition.setNum(Integer.parseInt(split[1]));
        this.streCondition = condition;
    }

    public void analyzeUpgradeCondition() {
        String[] split = this.upgradeConditions.split("_");
        UpgradeCondition condition = new UpgradeCondition();
        condition.setItemModelId(Integer.parseInt(split[0]));
        condition.setNum(Integer.parseInt(split[1]));
        condition.setEquipNeedLevel(Integer.parseInt(split[2]));
        this.upgradeCondition = condition;
    }

    public void analyzeBaseAttribute() {
        List<Attribute> list = new ArrayList<>();
        String[] split = baseAttribute.split(";");
        for (String str : split) {
            String[] attr = str.split(",");
            Attribute attribute = Attribute.valueOf(AttributeType.valueOf(attr[0]), Long.parseLong(attr[1]));
            list.add(attribute);
        }
        baseAttributeList = list;
    }


    public int getMaxLevel() {
        return maxLevel;
    }

    public int getUpgradeId() {
        return upgradeId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getEquipName() {
        return equipName;
    }

    public StrenCondition getStreCondition() {
        return streCondition;
    }

    public String getUpgradeConditions() {
        return upgradeConditions;
    }

    public UpgradeCondition getUpgradeCondition() {
        return upgradeCondition;
    }



    public String getEquipType() {
        return equipType;
    }

    public EquipCondition getEquipCondition() {
        return equipCondition;
    }

    public List<Attribute> getBaseAttributeList() {
        return baseAttributeList;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public void setUpgradeId(int upgradeId) {
        this.upgradeId = upgradeId;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getEquipConditions() {
        return equipConditions;
    }

    public void setEquipConditions(String equipConditions) {
        this.equipConditions = equipConditions;
    }

    public void setEquipCondition(EquipCondition equipCondition) {
        this.equipCondition = equipCondition;
    }

    public String getStreConditions() {
        return streConditions;
    }

    public void setStreConditions(String streConditions) {
        this.streConditions = streConditions;
    }

    public void setStreCondition(StrenCondition streCondition) {
        this.streCondition = streCondition;
    }

    public void setUpgradeConditions(String upgradeConditions) {
        this.upgradeConditions = upgradeConditions;
    }

    public void setUpgradeCondition(UpgradeCondition upgradeCondition) {
        this.upgradeCondition = upgradeCondition;
    }

    public String getBaseAttribute() {
        return baseAttribute;
    }

    public void setBaseAttribute(String baseAttribute) {
        this.baseAttribute = baseAttribute;
    }

    public void setBaseAttributeList(List<Attribute> baseAttributeList) {
        this.baseAttributeList = baseAttributeList;
    }
}

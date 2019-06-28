package com.game.user.item.packet;

import com.game.common.attribute.Attribute;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 16:41
 */
public class SM_ShowItemInfo {
    /**
     * 查看状态
     */
    private int status;

    /**
     * 道具名
     */
    private String itemName;
    /**
     * 道具唯一id
     */
    private long itemObjectId;
    /**
     * 数量
     */
    private int num;

    /**
     * 道具类型
     */
    private int itemType;
    /**
     * 装备类型
     */
    private int equipType;
    /**
     * 装备品质
     */
    private int equipQuality;
    /**
     *
     * 强化次数
     */
    private int strenNum;

    /**
     * 基础属性
     */
    private List<Attribute> baseAttributeList;
    /**
     * 强化属性
     */
    private List<Attribute> strenAttributeList;
    /**
     * 装备限制职业
     */
    private int jobType;
    /**
     * 装备限制等级
     */
    private int limitLevel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEquipQuality() {
        return equipQuality;
    }

    public void setEquipQuality(int equipQuality) {
        this.equipQuality = equipQuality;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getItemObjectId() {
        return itemObjectId;
    }

    public void setItemObjectId(long itemObjectId) {
        this.itemObjectId = itemObjectId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getEquipType() {
        return equipType;
    }

    public void setEquipType(int equipType) {
        this.equipType = equipType;
    }

    public int getStrenNum() {
        return strenNum;
    }

    public void setStrenNum(int strenNum) {
        this.strenNum = strenNum;
    }

    public List<Attribute> getBaseAttributeList() {
        return baseAttributeList;
    }

    public void setBaseAttributeList(List<Attribute> baseAttributeList) {
        this.baseAttributeList = baseAttributeList;
    }

    public List<Attribute> getStrenAttributeList() {
        return strenAttributeList;
    }

    public void setStrenAttributeList(List<Attribute> strenAttributeList) {
        this.strenAttributeList = strenAttributeList;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public int getLimitLevel() {
        return limitLevel;
    }

    public void setLimitLevel(int limitLevel) {
        this.limitLevel = limitLevel;
    }
}

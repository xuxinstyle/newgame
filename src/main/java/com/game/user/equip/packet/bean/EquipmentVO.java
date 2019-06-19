package com.game.user.equip.packet.bean;

import com.game.base.attribute.Attribute;
import com.game.role.constant.Job;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 11:44
 */
public class EquipmentVO {
    /**
     * 装备名称
     */
    private String equipName;
    /**
     * 装备品质
     */
    private int quality;
    /**
     * 部位
     */
    private int position;
    /**
     * 使用职业
     */
    private int job;
    /**
     * 使用等级
     */
    private int level;
    /**
     * 增加的属性
     */
    private List<Attribute> attributeList;
    public static EquipmentVO valueOf(int position){
        EquipmentVO equipmentVO = new EquipmentVO();
        equipmentVO.setPosition(position);
        return equipmentVO;
    }
    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}

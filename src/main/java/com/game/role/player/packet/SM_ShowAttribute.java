package com.game.role.player.packet;

import com.game.base.attribute.Attribute;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 11:42
 */
public class SM_ShowAttribute {

    private String playerName;
    /**
     * 一级属性
     */
    private List<Attribute> firstAttributeList;
    /**
     * 二级属性
     */
    private List<Attribute> secondAttributeList;

    /**
     * 其他属性
     */
    private List<Attribute> otherAttributeList;

    public List<Attribute> getOtherAttributeList() {
        return otherAttributeList;
    }

    public void setOtherAttributeList(List<Attribute> otherAttributeList) {
        this.otherAttributeList = otherAttributeList;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Attribute> getFirstAttributeList() {
        return firstAttributeList;
    }

    public void setFirstAttributeList(List<Attribute> firstAttributeList) {
        this.firstAttributeList = firstAttributeList;
    }

    public List<Attribute> getSecondAttributeList() {
        return secondAttributeList;
    }

    public void setSecondAttributeList(List<Attribute> secondAttributeList) {
        this.secondAttributeList = secondAttributeList;
    }
}

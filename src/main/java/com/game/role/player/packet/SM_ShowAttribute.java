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
    private List<Attribute> firstAttribute;
    /**
     * 二级属性
     */
    private List<Attribute> secondAttribute;

    public List<Attribute> getFirstAttribute() {
        return firstAttribute;
    }

    public void setFirstAttribute(List<Attribute> firstAttribute) {
        this.firstAttribute = firstAttribute;
    }

    public List<Attribute> getSecondAttribute() {
        return secondAttribute;
    }

    public void setSecondAttribute(List<Attribute> secondAttribute) {
        this.secondAttribute = secondAttribute;
    }
}

package com.game.role.skill.packet;

import com.game.base.attribute.Attribute;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 17:32
 */
public class SM_AddAttributeEffect {
    /**
     * 改变的属性列表
     */
    private List<Attribute> changeAttrs;

    public static SM_AddAttributeEffect valueOf(List<Attribute> attrs) {
        SM_AddAttributeEffect sm = new SM_AddAttributeEffect();
        sm.setChangeAttrs(attrs);
        return sm;
    }

    public List<Attribute> getChangeAttrs() {
        return changeAttrs;
    }

    public void setChangeAttrs(List<Attribute> changeAttrs) {
        this.changeAttrs = changeAttrs;
    }
}

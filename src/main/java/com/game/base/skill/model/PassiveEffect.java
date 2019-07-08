package com.game.base.skill.model;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 22:14
 */
public class PassiveEffect extends AbstractSkillEffect {
    /**
     * 被动技能增加的属性
     */
    List<Attribute> effectAttributes;

    public static PassiveEffect valueOf(String passiveEffectStr){
        PassiveEffect passiveEffect = new PassiveEffect();
        List<Attribute> attrs = new ArrayList<>();
        String[] attrStr = passiveEffectStr.split(StringUtil.FEN_HAO);
        for(String str:attrStr){
            String[] split = str.split(StringUtil.DOU_HAO);
            attrs.add(Attribute.valueOf(AttributeType.valueOf(split[0]),Long.parseLong(split[1])));
        }
        passiveEffect.setEffectAttributes(attrs);
        return passiveEffect;
    }

    public List<Attribute> getEffectAttributes() {
        return effectAttributes;
    }

    public void setEffectAttributes(List<Attribute> effectAttributes) {
        this.effectAttributes = effectAttributes;
    }
}

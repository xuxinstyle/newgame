package com.game.role.skilleffect.constant;

import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.role.skilleffect.model.AddMyselfAttributeEffect;
import com.game.role.skilleffect.model.DurationDamageEffect;
import com.game.role.skilleffect.model.PhysicalDamageEffect;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 18:22
 */
public enum SkillEffectType {
    /**
     * 伤害类型
     */
    PHYSICAL_DAMAGE(1, PhysicalDamageEffect.class),
    /**
     * 增加目标属性
     */
    ADD_ATTRIBUTE(2, AddMyselfAttributeEffect.class),
    /**
     * 持续减血效果
     */
    DURATION_DAMAGE(3, DurationDamageEffect.class),;

    private int id;

    private Class<? extends AbstractSkillEffect> skillEffectClazz;


    public AbstractSkillEffect create() {
        try {
            return this.skillEffectClazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成Effect实例[" + this.skillEffectClazz.getSimpleName() + "]错误");
        }
    }

    SkillEffectType(int id, Class<? extends AbstractSkillEffect> clz) {
        this.id = id;
        this.skillEffectClazz = clz;
    }

    public Class<? extends AbstractSkillEffect> getSkillEffectClazz() {
        return skillEffectClazz;
    }

    public void setSkillEffectClazz(Class<? extends AbstractSkillEffect> skillEffectClazz) {
        this.skillEffectClazz = skillEffectClazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

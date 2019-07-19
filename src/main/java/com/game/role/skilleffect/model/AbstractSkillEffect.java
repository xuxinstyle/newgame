package com.game.role.skilleffect.model;


import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.context.SkillUseContext;
import com.game.role.skilleffect.resource.SkillEffectResource;
import com.game.scence.fight.model.CreatureUnit;

/**
 * 技能效果 合并的判断 合并的实现 效果的失效 合并时间等 触发点
 * @Author：xuxin
 * @Date: 2019/7/8 21:00
 */
public abstract class AbstractSkillEffect {
    /**
     * 效果id
     */
    protected int effectId;
    /**
     * 持续时间
     */
    protected long duration;

    public void init(SkillEffectResource skillEffectResource) {
        this.effectId = skillEffectResource.getId();
        this.duration = skillEffectResource.getDuration();
    }

    public void doActive(SkillUseContext skillUseContext, CreatureUnit targetUnit) {

    }


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }


}

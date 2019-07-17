package com.game.role.skilleffect.service;

import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.role.skilleffect.resource.SkillEffectResource;

import java.util.Collection;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 21:26
 */
public interface SkillEffectService {
    /**
     * 获取SkillEffectResource
     *
     * @param id
     * @return
     */
    SkillEffectResource getSkillEffectResource(int id);

    /**
     * 获取技能效果
     *
     * @param effectId
     * @return
     */

    AbstractSkillEffect getSkillEffect(int effectId);

    /**
     * 获取所有的
     *
     * @return
     */
    Collection<SkillEffectResource> getSkillEffectResourceAll();
}

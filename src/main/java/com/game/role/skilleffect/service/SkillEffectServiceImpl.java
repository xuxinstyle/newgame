package com.game.role.skilleffect.service;

import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.role.skilleffect.resource.SkillEffectResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 21:26
 */
@Component
public class SkillEffectServiceImpl implements SkillEffectService {
    @Autowired
    private SkillEffectManager skillEffectManager;

    @Override
    public SkillEffectResource getSkillEffectResource(int id) {
        return skillEffectManager.getSkillEffectResource(id);
    }

    @Override
    public AbstractSkillEffect getSkillEffect(int effectId) {
        return skillEffectManager.getSkillEffect(effectId);
    }

    @Override
    public Collection<SkillEffectResource> getSkillEffectResourceAll() {
        return skillEffectManager.getSkillEffectResourceAll();
    }

}

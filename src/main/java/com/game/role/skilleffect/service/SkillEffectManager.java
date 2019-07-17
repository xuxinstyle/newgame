package com.game.role.skilleffect.service;

import com.game.role.skilleffect.constant.SkillEffectType;
import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.role.skilleffect.resource.SkillEffectResource;
import com.resource.anno.Init;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 21:27
 */
@Component
public class SkillEffectManager {
    @Autowired
    private StorageManager storageManager;
    /**
     * 效果id，效果实体
     */
    @Init("initSkill")
    private Map<Integer, AbstractSkillEffect> skillEffectMap = new HashMap<>();

    public SkillEffectResource getSkillEffectResource(int id) {
        return storageManager.getResource(id, SkillEffectResource.class);
    }

    public Collection<SkillEffectResource> getSkillEffectResourceAll() {
        return (Collection<SkillEffectResource>) storageManager.getResourceAll(SkillEffectResource.class);
    }

    public void initSkill() {
        Collection<SkillEffectResource> resourceAll = getSkillEffectResourceAll();
        for (SkillEffectResource resource : resourceAll) {
            String effectType = resource.getEffectType();
            SkillEffectType skillEffectType = SkillEffectType.valueOf(effectType);
            AbstractSkillEffect skillEffect = skillEffectType.create();
            skillEffect.init(resource);
            skillEffectMap.put(resource.getId(), skillEffect);
        }
    }

    public AbstractSkillEffect getSkillEffect(int effectId) {
        return skillEffectMap.get(effectId);
    }

    public Map<Integer, AbstractSkillEffect> getSkillEffectMap() {
        return skillEffectMap;
    }

    public void setSkillEffectMap(Map<Integer, AbstractSkillEffect> skillEffectMap) {
        this.skillEffectMap = skillEffectMap;
    }
}

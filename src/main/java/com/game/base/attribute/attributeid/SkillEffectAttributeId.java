package com.game.base.attribute.attributeid;

import com.game.SpringContext;
import com.game.role.skilleffect.constant.SkillEffectType;
import com.game.role.skilleffect.resource.SkillEffectResource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 9:54
 */
public class SkillEffectAttributeId implements AttributeId {

    private static final Map<Integer, SkillEffectAttributeId> skillEffectAttributeId =
            new HashMap<>(SkillEffectType.values().length);

    static {
        Collection<SkillEffectResource> skillEffectResourceAll = SpringContext.getSkillEffectService().getSkillEffectResourceAll();
        for (SkillEffectResource resource : skillEffectResourceAll) {
            skillEffectAttributeId.put(resource.getId(), new SkillEffectAttributeId(resource.getId()));
        }
    }

    private int id;

    public SkillEffectAttributeId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return "SkillEffectAttributeId{" + id + "}";
    }

    @Override
    public String toString() {
        return getName();
    }

    public static Map<Integer, SkillEffectAttributeId> getSkillEffectAttributeId() {
        return skillEffectAttributeId;
    }

    public static SkillEffectAttributeId getSkillAttributeId(int id) {
        return skillEffectAttributeId.get(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

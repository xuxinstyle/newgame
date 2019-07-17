package com.game.role.skill.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 17:01
 */
public enum SkillTargetType {
    /**
     * 自身
     */
    MYSELF,
    /**
     * 其他人
     */
    OTHER,;

    public static Map<String, SkillTargetType> skillTargetTypeMap = new HashMap<>();

    static {
        for (SkillTargetType type : SkillTargetType.values()) {
            skillTargetTypeMap.put(type.name(), type);
        }
    }
}

package com.game.role.skilleffect.context;

import java.util.EnumMap;
import java.util.Map;

/**
 * fixme:用于保存技能释放的中间参数
 * todo: 被动触发时产生的结果
 * @Author：xuxin
 * @Date: 2019/7/18 20:26
 */
public class SkillUseContext {
    /**
     * 参数
     */
    private Map<SkillUseContextEnm, Object> keyParamMap = new EnumMap<>(SkillUseContextEnm.class);

    public <T> T getParam(SkillUseContextEnm key) {
        return (T) keyParamMap.get(key);
    }

    public void putSkillContextEnum(SkillUseContextEnm key, Object value) {
        keyParamMap.put(key, value);
    }
}

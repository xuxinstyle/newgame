package com.game.role.skilleffect.context;

import java.util.EnumMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 20:26
 */
public class SkillUseContext {
    /**
     * 参数
     */
    private Map<SkillUseContextEnm, Object> keyParamMap = new EnumMap<>(SkillUseContextEnm.class);

    public Object getParam(SkillUseContextEnm key) {
        return keyParamMap.get(key);
    }

    public void putSkillContextEnum(SkillUseContextEnm enm, Object value) {
        keyParamMap.put(enm, value);
    }
}

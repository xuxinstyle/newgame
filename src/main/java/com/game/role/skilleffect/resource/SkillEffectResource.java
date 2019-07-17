package com.game.role.skilleffect.resource;

import com.game.util.AttributeAnalyzeUtil;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 19:50
 */
@LoadResource
public class SkillEffectResource {
    /**
     * 唯一id
     */
    private int id;
    /**
     * 效果名
     */
    private String effectName;
    /**
     * 效果类型
     */
    private String effectType;
    /**
     * 效果值
     */
    private String effectValue;

    /**
     * 持续时间
     */
    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(String effectValue) {
        this.effectValue = effectValue;
    }
}

package com.game.user.item.model;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 12:30
 */
public abstract class AbstractUseEffect {
    /**
     * 使用效果时长
     */
    protected long effectiveTime;
    /**
     * 初始化
     * @param effect
     */
    public abstract void init(String effect,Map<String,Object> param);

    /**
     * 使用
     * @param
     */
    public abstract void use(String accountId,int num);

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}

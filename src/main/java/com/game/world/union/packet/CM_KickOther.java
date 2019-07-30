package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 9:41
 */
public class CM_KickOther {
    /**
     * 目标
     */
    private String targetAccountId;

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}

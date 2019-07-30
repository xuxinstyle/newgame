package com.game.world.union.packet;

/**
 * 请求委任队长
 *
 * @Author：xuxin
 * @Date: 2019/7/30 14:18
 */
public class CM_AppointCaptain {
    /**
     * 目标id
     */
    private String targetAccountId;

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}

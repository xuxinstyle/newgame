package com.game.world.union.packet;

/**
 * 拒绝申请
 *
 * @Author：xuxin
 * @Date: 2019/7/29 16:48
 */
public class CM_RefuseApply {

    private String targetAccountId;

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}

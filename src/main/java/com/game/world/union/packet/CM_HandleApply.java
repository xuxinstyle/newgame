package com.game.world.union.packet;

/**
 * 同意申请
 *
 * @Author：xuxin
 * @Date: 2019/7/29 16:19
 */
public class CM_HandleApply {

    /**
     * 目标账号id
     */
    private String targetAccountId;


    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}

package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 11:31
 */
public class SM_RefuseApply {
    /**
     * 1 （目标者）你被管理员拒绝加入 2 （操作者）操作成功
     */
    private int status;

    public static SM_RefuseApply valueOf(int status) {
        SM_RefuseApply sm = new SM_RefuseApply();
        sm.setStatus(status);
        return sm;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 10:36
 */
public class SM_UpdatePermission {
    /**
     * 状态 1 成功 2 没有修改权限
     */
    private int status;

    public static SM_UpdatePermission valueOf(int status) {
        SM_UpdatePermission sm = new SM_UpdatePermission();
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

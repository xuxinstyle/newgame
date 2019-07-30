package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 14:19
 */
public class SM_AppointCaptain {
    /**
     * 状态 1 成功 2 目标不在工会
     */
    private int status;

    public static SM_AppointCaptain valueOf(int status) {
        SM_AppointCaptain sm = new SM_AppointCaptain();
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

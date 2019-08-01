package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 9:41
 */
public class SM_KickOther {
    /**
     * 状态 1 成功 2 目标不在工会中 3 你被移出工会 4 踢人失败
     */
    private int status;

    public static SM_KickOther valueOf(int status) {
        SM_KickOther sm = new SM_KickOther();
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

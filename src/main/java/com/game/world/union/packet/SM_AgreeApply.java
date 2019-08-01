package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 15:10
 */
public class SM_AgreeApply {
    /**
     * 0 你已经加入工会 1 玩家不在申请列表 2 玩家加入其他行会 3 工会人数已满 4 你不在行会 5 你没有权限
     */
    private int status;

    public static SM_AgreeApply valueOf(int status) {
        SM_AgreeApply sm = new SM_AgreeApply();
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

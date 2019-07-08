package com.game.scence.visible.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/5 11:12
 */
public class SM_ChangeMapErr {
    /**
     * 状态 1 成功 2 失败 3 你已经在当前地图了无法切换
     */
    private int status;
    public static SM_ChangeMapErr valueOf(int status){
        SM_ChangeMapErr sm = new SM_ChangeMapErr();
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

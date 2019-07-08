package com.game.scence.visible.packet;

/**
 * 进入地图失败
 * @Author：xuxin
 * @Date: 2019/7/5 11:29
 */
public class SM_EnterMapErr {
    /**
     * 进入地图状态 1成功 2 失败
     */
    private int status;
    public static SM_EnterMapErr valueOf(int status){
        SM_EnterMapErr sm = new SM_EnterMapErr();
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

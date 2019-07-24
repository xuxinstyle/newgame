package com.game.world.hopetower.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 16:02
 */
public class SM_CloseScene {
    /**
     * 1 副本挑战成功 2 挑战失败
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

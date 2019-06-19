package com.game.user.item.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 12:03
 */
public class SM_RemoveItemFormPack {
    /**
     * 1 成功 0 失败
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

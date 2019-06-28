package com.game.register.packet;

import org.msgpack.annotation.Message;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:36
 * @id 6
 */
public class SM_Register {
    /**
     * true 注册成功，false 注册失败
     */
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

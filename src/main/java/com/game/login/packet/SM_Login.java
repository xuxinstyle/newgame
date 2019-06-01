package com.game.login.packet;

import org.msgpack.annotation.Message;

/**
 * @Author：xuxin
 * @Date: 2019/5/18 15:55
 * @Id 3
 */
@Message
public class SM_Login {
    //登录状态 1 成功， 0 失败
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

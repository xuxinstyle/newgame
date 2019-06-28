package com.game.register.packet;

import org.msgpack.annotation.Message;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:36
 * @id 5
 */
public class CM_Register {
    /**
     * 账号
     */
    String username;
    /**
     * 密码
     */
    String passward;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}

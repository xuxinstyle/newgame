package com.game.login.packet;

import org.msgpack.annotation.Message;

/**
 *  请求登录
  * @Author：xuxin
  * @Date: 2019/5/18 15:53
  * @Id 1
  */
@Message
public class CM_Login {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String passward;

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

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
    private String accountId;

    private String passWord;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

package com.game.login.packet;

import org.msgpack.annotation.Message;

/**
  * @Author：xuxin
  * @Date: 2019/5/18 15:53
  * @Id 1
  */
@Message
public class CM_Login {
    private String accountId;

    private String passWord;

}

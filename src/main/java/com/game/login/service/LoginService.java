package com.game.login.service;

import com.socket.core.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/1 15:58
 */
public interface LoginService {
    void login(TSession session, String username, String passward);
}

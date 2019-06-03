package com.game.register.service;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:49
 */
public interface RegisterService {
    /**
     * 注册
     * @param username
     * @param passward
     */
    void doRegister(String username, String passward, TSession session);

}

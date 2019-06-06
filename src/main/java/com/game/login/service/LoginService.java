package com.game.login.service;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/1 15:58
 */
public interface LoginService {
    /**
     * 登录操作
     * @param session
     * @param username
     * @param passward
     */
    void login(TSession session, String username, String passward);

    /**
     * 登出操作
     * @param session
     * @param accountId
     */
    void logout(TSession session, String accountId);
}

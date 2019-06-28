package com.game.gm.service;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/28 18:03
 */
public interface GmService {
    /**
     * 执行gm命令
     * @param session
     * @param command
     */
    void doGmCommand(TSession session, String command);
}

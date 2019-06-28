package com.game.base.identify.service;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 12:00
 */
public interface SessionService {
    /**
     *
     * @param accountId
     * @return
     */
    TSession getSession(String accountId);
    /**
     * 发送协议
     * @param session
     * @param packet
     */
    void sendPacket(TSession session, Object packet);
    /**
     * 发送协议
     */
    void sendPacket(String accountId, Object packet);
}

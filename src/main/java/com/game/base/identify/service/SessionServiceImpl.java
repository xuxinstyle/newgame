package com.game.base.identify.service;

import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 12:00
 */
@Component
public class SessionServiceImpl implements SessionService{
    @Autowired
    private SessionManager sessionManager;

    @Override
    public TSession getSession(String accountId) {
        return sessionManager.getSessionByAccount(accountId);
    }

    @Override
    public void sendPacket(TSession session, Object packet) {
        if(session!=null) {
            session.sendPacket(packet);
        }
    }

    @Override
    public void sendPacket(String accountId, Object packet) {
        TSession session = sessionManager.getSessionByAccount(accountId);
        if(session!=null){
            session.sendPacket(packet);
        }
    }


}

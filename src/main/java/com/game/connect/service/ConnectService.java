package com.game.connect.service;

import com.game.connect.packet.CM_Connect;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:05
 */
public interface ConnectService {
    void connect(TSession session, CM_Connect req);
}

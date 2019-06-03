package com.game.connect.service;

import com.game.connect.packet.CM_Connect;
import com.socket.core.session.SessionUtil;
import com.socket.core.session.TSession;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:06
 */
@Component
public class ConnectServiceImpl implements ConnectService{
    @Override
    public void connect(TSession session, CM_Connect req) {

    }
}

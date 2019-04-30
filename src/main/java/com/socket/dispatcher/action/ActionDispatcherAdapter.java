package com.socket.dispatcher.action;

import com.socket.core.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:52
 */
public class ActionDispatcherAdapter implements IActionDispatcher {

    @Override
    public void open(TSession session) {

    }

    @Override
    public void connect(TSession session) {

    }

    @Override
    public void handle(TSession session, int opIndex, Object packet, long decodeTime) {

    }

    @Override
    public void send(TSession session, int opIndex, Object packet, long encodeTime) {

    }

    @Override
    public void close(TSession session) {

    }
}

package com.socket.dispatcher.action;

import com.socket.core.session.TSession;

/**
 * FIXME:此类只用了handle接口，后续增加过滤器可能会使用其他接口
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
    public void handle(TSession session, int opIndex, Object packet) {

    }

    @Override
    public void send(TSession session, int opIndex, Object packet) {

    }

    @Override
    public void close(TSession session) {

    }
}

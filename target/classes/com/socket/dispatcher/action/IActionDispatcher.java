package com.socket.dispatcher.action;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:53
 */
public interface IActionDispatcher {
    void open(TSession session);
    void connect(TSession session);
    void handle(TSession session, int opIndex, Object packet);
    void send(TSession session, int opIndex, Object packet);
    void close(TSession session);
}

package com.netty.dispatcher.action;

import com.netty.core.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:53
 */
public interface IActionDispatcher {
    void open(TSession session);
    void connect(TSession session);
    void handle(TSession session, int opIndex, Object packet,long decodeTime);
    void send(TSession session, int opIndex, Object packet, long encodeTime);
    void close(TSession session);
}

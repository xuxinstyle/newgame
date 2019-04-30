package com.socket.dispatcher.core;

import com.socket.core.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 17:51
 */
public interface IHandlerInvoke {
    Object invoke(TSession session, int opIndex, Object packet);
}

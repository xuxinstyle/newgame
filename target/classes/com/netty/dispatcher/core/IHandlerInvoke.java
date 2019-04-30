package com.netty.dispatcher.core;

import com.netty.core.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 17:51
 */
public interface IHandlerInvoke {
    Object invoke(TSession session, int opIndex, Object packet);
}

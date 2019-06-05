package com.socket.dispatcher.executor;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:57
 */
// TODO: 这里还没用上，后续加了线程池再用
public interface IIdentifyThreadPool {
    void addSessionTask(TSession session, Runnable task);
}

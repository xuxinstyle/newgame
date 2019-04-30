package com.socket.dispatcher.executor;

import com.socket.core.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:57
 */
public interface IIdentifyThreadPool {
    void addSessionTask(TSession session, Runnable task);
}

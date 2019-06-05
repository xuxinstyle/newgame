package com.socket.dispatcher.executor;

import com.socket.Utils.NameThreadFactory;
import com.socket.core.session.TSession;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:58
 */
// TODO: 这里还没用上，后续加了线程池再用
public class IdentifyThreadPoolExecutor extends ThreadPoolExecutor implements IIdentifyThreadPool{

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_KEEP_ALIVE = 20;
    public IdentifyThreadPoolExecutor(){
        super(DEFAULT_INITIAL_THREAD_POOL_SIZE, DEFAULT_INITIAL_THREAD_POOL_SIZE, DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), new NameThreadFactory("IdentifyThread"));

    }
    @Override
    public void addSessionTask(TSession session, Runnable task) {
        if(isShutdown()){
            getRejectedExecutionHandler().rejectedExecution(task,this);
        }

    }
}

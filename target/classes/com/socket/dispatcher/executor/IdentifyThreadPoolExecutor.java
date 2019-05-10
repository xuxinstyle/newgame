package com.socket.dispatcher.executor;

import com.socket.Utils.NameThreadFactory;
import com.socket.core.TSession;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:58
 */
public class IdentifyThreadPoolExecutor extends ThreadPoolExecutor implements IIdentifyThreadPool{

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_KEEP_ALIVE = 30;
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

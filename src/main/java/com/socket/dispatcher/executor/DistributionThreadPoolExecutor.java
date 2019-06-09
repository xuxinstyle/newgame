package com.socket.dispatcher.executor;

import com.socket.Utils.NameThreadFactory;
import com.socket.core.session.TSession;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * TODO:可能需要处理将协议分发到对应的线程池处理，等以后建立了线程模型后使用
 * @Author：xuxin
 * @Date: 2019/4/29 14:58
 */

public class DistributionThreadPoolExecutor extends ThreadPoolExecutor implements IIdentifyThreadPool{

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_KEEP_ALIVE = 20;
    public DistributionThreadPoolExecutor(){
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

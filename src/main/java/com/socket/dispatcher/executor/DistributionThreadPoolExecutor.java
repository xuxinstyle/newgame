package com.socket.dispatcher.executor;

import com.socket.Utils.NameThreadFactory;
import com.socket.core.session.TSession;

import java.util.concurrent.*;

/**
 *
 * TODO:可能需要处理将协议分发到对应的线程池处理，等以后建立了线程模型后使用
 * @Author：xuxin
 * @Date: 2019/4/29 14:58
 */

public class DistributionThreadPoolExecutor implements IIdentifyThreadPool{

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService[] service = new ExecutorService[DEFAULT_INITIAL_THREAD_POOL_SIZE];
    public DistributionThreadPoolExecutor(){
        for (int i = 0;i < DEFAULT_INITIAL_THREAD_POOL_SIZE ;i++){
            service[i] = Executors.newSingleThreadExecutor();
        }
    }
    @Override
    public void addSessionTask(TSession session, Runnable task) {
       service[Math.abs(session.getId())% DEFAULT_INITIAL_THREAD_POOL_SIZE].execute(task);
    }
}

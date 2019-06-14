package com.game.base.executor.common;

import com.game.base.executor.common.Impl.AbstractCommonDelayCommand;
import com.game.base.executor.common.Impl.AbstractCommonRateCommand;
import com.game.base.executor.common.Impl.AbstractCommonCommand;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import com.socket.dispatcher.core.ActionDispatcher;
import com.socket.dispatcher.executor.NameThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 22:07
 */
@Component
public class CommonExecutor {
    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ThreadPoolExecutor[] COMMON_SERVICE = new ThreadPoolExecutor[DEFAULT_INITIAL_THREAD_POOL_SIZE];

    public void start(){
        NameThreadFactory nameThreadFactory = new NameThreadFactory("CommondThread");
        for (int i = 0;i < DEFAULT_INITIAL_THREAD_POOL_SIZE ;i++){
            COMMON_SERVICE[i] = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),nameThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            /** 拒绝策略选择DiscardPolicy 对拒绝的任务无息抛弃无消息*/
            COMMON_SERVICE[i].prestartAllCoreThreads();
        }
    }
    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    public void schedule(AbstractCommonRateCommand command){
        pool.scheduleAtFixedRate(()->{
            if(!command.isCanceled()) {
                command.active();
            }
        },command.getDelay(),command.getPeriod(),TimeUnit.SECONDS);
    }
    public void schedule(AbstractCommonDelayCommand command){
        pool.schedule(()->{
            if(!command.isCanceled()) {
                command.active();
            }
        },command.getDelay(),TimeUnit.SECONDS);
    }

    public void addTask(AbstractCommonCommand command){

        Object key = command.getKey();
        int modIndex = command.modIndex(DEFAULT_INITIAL_THREAD_POOL_SIZE);
        COMMON_SERVICE[modIndex].submit(() -> {
            if(!command.isCanceled()){
                command.active();
            }
        });
    }
}

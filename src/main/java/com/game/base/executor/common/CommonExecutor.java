package com.game.base.executor.common;

import com.game.base.executor.NameThreadFactory;
import com.game.base.executor.common.impl.AbstractCommonDelayCommand;
import com.game.base.executor.common.impl.AbstractCommonRateCommand;
import com.game.base.executor.common.impl.AbstractCommonCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 22:07
 */
@Component
public class CommonExecutor {
    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors()/3;
    /**
     * 定时线程池核心线程数
     */
    private static final Integer POOL_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE > 2 ? DEFAULT_INITIAL_THREAD_POOL_SIZE:2;
    /**
     * 账号线程池核心线程数
     */
    private static final Integer COMMON_SERVICE_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE*2 > 4 ? DEFAULT_INITIAL_THREAD_POOL_SIZE*2:4;

    private static final ThreadPoolExecutor[] COMMON_SERVICE = new ThreadPoolExecutor[COMMON_SERVICE_SIZE];

    private static NameThreadFactory scheduleNameThreadFactory = new NameThreadFactory("CommonScheduleExecutorThread");

    private static final ScheduledExecutorService COMMON_SCHEDULE_POOL = Executors.newScheduledThreadPool(POOL_SIZE,scheduleNameThreadFactory);

    private static Logger logger = LoggerFactory.getLogger(CommonExecutor.class);
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


    public void schedule(AbstractCommonRateCommand command,long delay, long period){
        command.refreshState();
        COMMON_SCHEDULE_POOL.scheduleAtFixedRate(()->
             addTask(command)
         ,delay ,period ,TimeUnit.MILLISECONDS);
    }

    public void schedule(AbstractCommonDelayCommand command){
        command.refreshState();
        command.setFuture(COMMON_SCHEDULE_POOL.schedule(()->
            addTask(command)
        ,command.getDelay(),TimeUnit.MILLISECONDS));
    }

    public void addTask(AbstractCommonCommand command){

        int modIndex = command.modIndex(DEFAULT_INITIAL_THREAD_POOL_SIZE);
        Object key = command.getKey();
        COMMON_SERVICE[modIndex].submit(() -> {
            try {
                if (!command.isCanceled()) {
                    command.active();
                }
            } catch (Exception e) {
                logger.error("CommonExecutor执行：" + command.getName() + ",key:" + key, e);
            }
        });
    }
}

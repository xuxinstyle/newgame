package com.game.base.executor.account;

import com.game.base.executor.account.Impl.AbstractAccountCommand;
import com.socket.dispatcher.executor.NameThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 16:19
 */
@Component
public class AccountThreadPoolExecutor{

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ThreadPoolExecutor[] ACCOUNT_SERVICE = new ThreadPoolExecutor[DEFAULT_INITIAL_THREAD_POOL_SIZE];

    public void start(){
        NameThreadFactory nameThreadFactory = new NameThreadFactory("AccountExecutorThread");
        for (int i = 0;i < DEFAULT_INITIAL_THREAD_POOL_SIZE ;i++){
            ACCOUNT_SERVICE[i] = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),nameThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            /** 拒绝策略选择DiscardPolicy 对拒绝的任务无息抛弃无消息*/
            ACCOUNT_SERVICE[i].prestartAllCoreThreads();
        }
    }

    public void addTask(AbstractAccountCommand accountCommond){
        Object key = accountCommond.getKey();
        int modIndex = accountCommond.modIndex(DEFAULT_INITIAL_THREAD_POOL_SIZE);
        ACCOUNT_SERVICE[modIndex].submit(() -> {
           if(!accountCommond.isCanceled()){
               accountCommond.active();
           }
        });
    }
}

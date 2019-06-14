package com.game.base.executor.scene;

import com.game.base.executor.account.Impl.AbstractAccountCommand;
import com.game.base.executor.scene.Impl.AbstractSceneCommand;
import com.socket.dispatcher.executor.NameThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 16:19
 */
@Component
public class SceneThreadPoolExecutor {

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ThreadPoolExecutor[] SCENE_SERVICE = new ThreadPoolExecutor[DEFAULT_INITIAL_THREAD_POOL_SIZE];

    public void start(){
        NameThreadFactory nameThreadFactory = new NameThreadFactory("SceneExecutorThread");
        for (int i = 0;i < DEFAULT_INITIAL_THREAD_POOL_SIZE ;i++){
            SCENE_SERVICE[i] = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),nameThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            /** 拒绝策略选择DiscardPolicy 对拒绝的任务无息抛弃无消息*/
            SCENE_SERVICE[i].prestartAllCoreThreads();
        }
    }

    public void addTask(AbstractSceneCommand accountCommond){
        Object key = accountCommond.getKey();
        int modIndex = accountCommond.modIndex(DEFAULT_INITIAL_THREAD_POOL_SIZE);
        SCENE_SERVICE[modIndex].submit(() -> {
           if(!accountCommond.isCanceled()){
               accountCommond.active();
           }
        });
    }
}

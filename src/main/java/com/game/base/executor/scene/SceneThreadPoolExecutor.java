package com.game.base.executor.scene;

import com.game.base.executor.NameThreadFactory;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 16:19
 */
@Component
public class SceneThreadPoolExecutor {

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int DEFAULT_SCHEDULE_INITIAL_THREAD_POOL_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE/2;

    private static final Integer POOL_SIZE = DEFAULT_SCHEDULE_INITIAL_THREAD_POOL_SIZE > 2 ? DEFAULT_SCHEDULE_INITIAL_THREAD_POOL_SIZE : 2;

    private static final ThreadPoolExecutor[] SCENE_SERVICE = new ThreadPoolExecutor[DEFAULT_INITIAL_THREAD_POOL_SIZE];

    private static NameThreadFactory scheduleNameThreadFactory = new NameThreadFactory("SceneScheduleExecutorThread");

    private static final ScheduledExecutorService SCENE_SCHEDULE_POOL = Executors.newScheduledThreadPool(POOL_SIZE,scheduleNameThreadFactory);


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

    public void schedule(AbstractSceneCommand command, long delay){
        command.refreshState();
        SCENE_SCHEDULE_POOL.schedule(()->addTask(command),delay,TimeUnit.MILLISECONDS);
    }

    public void schedule(AbstractSceneCommand command, long delay, long period){
        command.refreshState();
        SCENE_SCHEDULE_POOL.scheduleAtFixedRate(()->addTask(command),
                delay, period,TimeUnit.MILLISECONDS);
    }
}

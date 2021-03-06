package com.game.base.executor.scene;

import com.game.base.executor.NameThreadFactory;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.common.exception.RequestException;
import com.game.util.SendPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 16:19
 */
@Component
public class SceneThreadPoolExecutor {

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors()/3;
    /**
     * 定时器线程池核心线程数
     */
    private static final Integer POOL_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE >2 ? DEFAULT_INITIAL_THREAD_POOL_SIZE :2;
    /**
     * 账号线程池核心线程数
     */
    private static final Integer SCENE_SERVICE_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE*2 >4 ? DEFAULT_INITIAL_THREAD_POOL_SIZE*2 :4;

    private static final ThreadPoolExecutor[] SCENE_SERVICE = new ThreadPoolExecutor[SCENE_SERVICE_SIZE];

    private static NameThreadFactory scheduleNameThreadFactory = new NameThreadFactory("SceneScheduleExecutorThread");

    private static final ScheduledExecutorService SCENE_SCHEDULE_POOL = Executors.newScheduledThreadPool(POOL_SIZE,scheduleNameThreadFactory);

    private static final Logger logger = LoggerFactory.getLogger(SceneThreadPoolExecutor.class);
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

    public void addTask(AbstractSceneCommand command) {
        Object key = command.getKey();
        int modIndex = command.modIndex(DEFAULT_INITIAL_THREAD_POOL_SIZE);
        SCENE_SERVICE[modIndex].submit(() -> {
            try {
                if (!command.isCanceled()) {
                    command.active();
                }
            } catch (RequestException e) {
                SendPacketUtil.send(command.getAccountId(), e.getErrorCode());
            } catch (Exception e) {
                logger.error("SceneThreadPoolExecutor执行：" + command.getName() + ",key:" + key, e);
            }
        });
    }

    public void schedule(AbstractSceneCommand command, long delay){
        command.refreshState();
        command.setFuture(SCENE_SCHEDULE_POOL.schedule(()->
                addTask(command),delay,TimeUnit.MILLISECONDS));
    }

    public void schedule(AbstractSceneCommand command, long delay, long period){
        command.refreshState();
        command.setFuture(SCENE_SCHEDULE_POOL.scheduleAtFixedRate(()->
                        addTask(command), delay, period,TimeUnit.MILLISECONDS));
    }
}

package com.game.base.executor.account;

import com.game.base.executor.NameThreadFactory;
import com.game.base.executor.account.impl.AbstractAccountCommand;
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
public class AccountThreadPoolExecutor{

    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors()/3;
    /**
     * 定时线程池核心线程数
     */
    private static final Integer POOL_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE > 2 ? DEFAULT_INITIAL_THREAD_POOL_SIZE:2;
    /**
     * 账号线程池核心线程数
     */
    private static final Integer ACCOUNT_SERVICE_SIZE = DEFAULT_INITIAL_THREAD_POOL_SIZE*2 > 4 ? DEFAULT_INITIAL_THREAD_POOL_SIZE*2:4;

    private static final ThreadPoolExecutor[] ACCOUNT_SERVICE = new ThreadPoolExecutor[ACCOUNT_SERVICE_SIZE];

    private static NameThreadFactory scheduleNameThreadFactory = new NameThreadFactory("AccountScheduleExecutorThread");

    private static final ScheduledExecutorService ACCOUNT_SCHEDULE_POOL = Executors.newScheduledThreadPool(POOL_SIZE,scheduleNameThreadFactory);

    private static final Logger logger = LoggerFactory.getLogger(AccountThreadPoolExecutor.class);
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

        int modIndex = accountCommond.modIndex(DEFAULT_INITIAL_THREAD_POOL_SIZE);
        Object key = accountCommond.getKey();
        ACCOUNT_SERVICE[modIndex].submit(() -> {
            try {
                if (!accountCommond.isCanceled()) {
                    accountCommond.active();
                }
            } catch (RequestException e) {
                SendPacketUtil.send(accountCommond.getAccountId(), e.getErrorCode());
            } catch (Exception e) {
                logger.error("AccountThreadPoolExecutor执行：" + accountCommond.getName() + ",key:" + key, e);
            }
        });
    }

    public void addTask(final String accountId, final String taskName, final Runnable task) {
        ACCOUNT_SERVICE[modIndex(accountId)].execute(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (RequestException e) {
                    SendPacketUtil.send("任务：[" + taskName + "]出错", e.getErrorCode());
                }
            }
        });
    }

    private int modIndex(String accountId) {
        return Math.abs(accountId.hashCode() % DEFAULT_INITIAL_THREAD_POOL_SIZE);
    }

    /**
     * 延时任务
     * @param command
     * @param delay
     */
    public final void schedule(AbstractAccountCommand command, long delay){
        command.refreshState();
        command.setFuture(ACCOUNT_SCHEDULE_POOL.schedule(()->
                addTask(command),delay,TimeUnit.MILLISECONDS
        ));
    }

    /**
     * 周期任务
     * @param command
     * @param delay
     */
    public final void schedule(AbstractAccountCommand command, long delay, long period){
        command.refreshState();
        command.setFuture(ACCOUNT_SCHEDULE_POOL.scheduleAtFixedRate(()->
                addTask(command),delay,period,TimeUnit.MILLISECONDS
        ));
    }

}

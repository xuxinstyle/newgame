package com.game.base.executor.account;

import com.game.base.executor.account.impl.AbstractAccountCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:11
 */
public interface AccountExecutorService {
    /**
     * 开启线程
     */
    void init();

    /**
     * 将command抛到对应的线程中执行
     * @param commond
     */
    public void submit(AbstractAccountCommand commond);

    /**
     * 执行特定任务
     * @param accountId
     * @param taskName
     * @param task
     */
    void addTask(String accountId, final String taskName, final Runnable task);

}

package com.game.base.executor.account.Impl;

import com.game.base.executor.ICommand;

import java.util.concurrent.ScheduledFuture;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:21
 */
public abstract class AbstractCommand implements ICommand {
    /**
     * 是否被取消
     */
    private boolean isCanceled = false;
    /**
     * 定时任务句柄
     */
    private ScheduledFuture future;

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public void cancel() {
        if(future!=null){
            future.cancel(true);
        }
        isCanceled = true;
    }

    @Override
    public void refreshState() {
        isCanceled = false;
    }

    public ScheduledFuture getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }
}

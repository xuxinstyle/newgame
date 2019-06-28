package com.game.base.executor;

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
        /**
         * 这里传false，表示如果这个任务正在执行，则不会取消任务
         * true：表示如果这个任务正在执行，则这个任务将会取消
         */
        if(future!=null){
            future.cancel(false);
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

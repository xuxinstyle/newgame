package com.game.base.executor.common.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 12:27
 */
public abstract class AbstractCommonDelayCommand extends AbstractCommonCommand {
    /** 延时*/
    private long delay;

    public AbstractCommonDelayCommand(long delay){
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}

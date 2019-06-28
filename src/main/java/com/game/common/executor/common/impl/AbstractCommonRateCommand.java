package com.game.common.executor.common.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 12:12
 */
public abstract class AbstractCommonRateCommand extends AbstractCommonDelayCommand {
    /**
     * 周期
     */
    private long period;
    public AbstractCommonRateCommand(long period, long delay){
        super(delay);
        this.period = period;
    }
    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}

package com.game.base.executor.account.Impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 11:24
 */
public abstract class AbstractAccountRateCommand extends AbstractAccountDelayCommand {
    /**
     * 周期
     */
    private long period;
    /**
     * @param delay
     * @param accountId
     */
    public AbstractAccountRateCommand(long delay,long period, String accountId) {
        super(delay, accountId);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}

package com.game.base.executor.account.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 11:19
 */
public abstract class AbstractAccountDelayCommand extends AbstractAccountCommand {

    private long delay;
    /**
     *
     * @param delay
     * @param accountId
     */
    public AbstractAccountDelayCommand(long delay,String accountId) {
        super(accountId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}

package com.game.base.executor.scene.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 19:48
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {
    /**
     * 周期
     */
    private long period;

    public AbstractSceneRateCommand(int mapId, long delay, long period,String accountId) {
        super(mapId, delay,accountId);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}

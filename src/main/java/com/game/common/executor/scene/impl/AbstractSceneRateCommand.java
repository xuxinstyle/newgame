package com.game.common.executor.scene.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 19:48
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {
    /**
     * 周期
     */
    private long period;

    public AbstractSceneRateCommand(int mapId, long delay, long period) {
        super(mapId, delay);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}

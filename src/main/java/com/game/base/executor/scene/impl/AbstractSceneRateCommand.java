package com.game.base.executor.scene.impl;

import com.game.scence.base.model.AbstractScene;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 19:48
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {
    /**
     * 周期
     */
    private long period;

    public AbstractSceneRateCommand(int mapId, int sceneId, long delay, long period, String accountId) {
        super(mapId, sceneId, delay, accountId);
        this.period = period;
    }

    public AbstractSceneRateCommand(AbstractScene scene, long delay, long period, String accountId) {
        super(scene, accountId, delay);
        this.period = period;
    }
    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}

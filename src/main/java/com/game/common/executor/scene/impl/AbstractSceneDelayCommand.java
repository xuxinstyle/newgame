package com.game.common.executor.scene.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 19:46
 */
public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {

    long delay;

    public AbstractSceneDelayCommand(int mapId,long delay) {
        super(mapId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}

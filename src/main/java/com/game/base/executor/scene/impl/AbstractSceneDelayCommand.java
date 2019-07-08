package com.game.base.executor.scene.impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 19:46
 */
public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {

    long delay;

    public AbstractSceneDelayCommand(int mapId,long delay,String accountId) {
        super(mapId,0, accountId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}

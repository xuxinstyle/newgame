package com.game.base.executor.scene.impl;

import com.game.scence.base.model.AbstractScene;

/**
 * @Author：xuxin
 * @Date: 2019/6/24 19:46
 */
public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {

    long delay;

    public AbstractSceneDelayCommand(int mapId, int sceneId, long delay, String accountId) {
        super(mapId, sceneId, accountId);
        this.delay = delay;
    }

    public AbstractSceneDelayCommand(AbstractScene scene, String accountId, long delay) {
        super(scene, accountId);
        this.delay = delay;
    }
    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}

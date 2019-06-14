package com.game.base.executor.scene.Impl;

import com.game.base.executor.AbstractCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:25
 */
public abstract class AbstractSceneCommand extends AbstractCommand {
    
    /**TODO:暂时只有mapId等以后场景优化后再增加场景id*/
    private int mapId;

    public AbstractSceneCommand(int mapId){
        this.mapId = mapId;
    }

    public int getMapId() {
        return mapId;
    }

    @Override
    public Object getKey() {
        return mapId;
    }

    @Override
    public int modIndex(int poolsize) {
        return Math.abs(mapId % poolsize);
    }
}

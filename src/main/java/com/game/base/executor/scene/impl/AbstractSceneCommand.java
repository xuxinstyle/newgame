package com.game.base.executor.scene.impl;

import com.game.base.executor.AbstractCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:25
 */
public abstract class AbstractSceneCommand extends AbstractCommand {
    
    /**TODO:暂时只有mapId等以后场景优化后再增加场景id*/
    private int mapId;
    /**
     * 相当于分线id
     */
    private int sceneId;
    /**
     * 账号id
     */
    private String accountId;

    public AbstractSceneCommand(int mapId,int sceneId,String accountId){
        this.mapId = mapId;
        this.sceneId = sceneId;
        this.accountId = accountId;
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

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

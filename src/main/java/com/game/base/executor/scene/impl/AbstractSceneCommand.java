package com.game.base.executor.scene.impl;

import com.game.base.executor.AbstractCommand;
import com.game.scence.base.model.AbstractScene;

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
    /**
     * 场景对象
     */
    private AbstractScene scene;

    /**
     * @param mapId
     * @param sceneId
     * @param accountId
     */
    public AbstractSceneCommand(int mapId, int sceneId, String accountId) {
        this.mapId = mapId;
        this.sceneId = sceneId;
        this.accountId = accountId;
    }

    public AbstractSceneCommand(AbstractScene scene, String accountId) {
        this.mapId = scene.getMapId();
        this.sceneId = scene.getSceneId();
        this.accountId = accountId;
        this.scene = scene;
    }

    public int getMapId() {
        return mapId;
    }

    public AbstractScene getScene() {
        return scene;
    }

    public void setScene(AbstractScene scene) {
        this.scene = scene;
    }

    @Override
    public Integer getKey() {
        return (mapId | sceneId);
    }

    @Override
    public int modIndex(int poolsize) {
        return Math.abs(getKey() % poolsize);
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

package com.game.world.hopetower.event;

import com.event.event.IEvent;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 21:29
 */
public class MonsterDeadEvent implements IEvent {
    /**
     * mapID
     */
    private int mapId;
    /**
     * 场景id
     */
    private int sceneId;
    /**
     * 怪物id
     */
    private int monsterId;
    /**
     * 谁杀死的
     */
    private String accountId;

    public static MonsterDeadEvent valueOf(CreatureUnit attackerUnit, int monsterId) {
        MonsterDeadEvent event = new MonsterDeadEvent();
        event.setAccountId(attackerUnit.getAccountId());
        event.setMapId(attackerUnit.getMapId());
        event.setSceneId(attackerUnit.getScene().getSceneId());
        event.setMonsterId(monsterId);
        return event;
    }

    @Override
    public long getId() {
        return 0;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getMapId() {
        return mapId;
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

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }
}

package com.game.scence.monster.event;

import com.event.event.IEvent;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 17:14
 */
public class CreatureDeadEvent implements IEvent {
    /**
     * 怪物战斗单元
     */
    private CreatureUnit monsterUnit;
    /**
     * 地图id
     */
    private int mapId;
    /**
     *
     * @param monsterUnit
     * @param mapId
     * @return
     */

    public static CreatureDeadEvent valueOf(CreatureUnit monsterUnit, int mapId){
        CreatureDeadEvent event = new CreatureDeadEvent();
        event.setMonsterUnit(monsterUnit);
        event.setMapId(mapId);
        return event;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public CreatureUnit getMonsterUnit() {
        return monsterUnit;
    }

    public void setMonsterUnit(CreatureUnit monsterUnit) {
        this.monsterUnit = monsterUnit;
    }

    @Override
    public long getId() {
        return 0;
    }
}

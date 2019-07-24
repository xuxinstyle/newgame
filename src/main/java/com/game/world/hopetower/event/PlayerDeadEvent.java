package com.game.world.hopetower.event;

import com.event.event.IEvent;
import com.game.scence.fight.model.PlayerUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 21:42
 */
public class PlayerDeadEvent implements IEvent {

    private PlayerUnit playerUnit;

    public static PlayerDeadEvent valueOf(PlayerUnit playerUnit) {
        PlayerDeadEvent event = new PlayerDeadEvent();
        event.setPlayerUnit(playerUnit);
        return event;
    }

    @Override
    public long getId() {
        return 0;
    }

    public PlayerUnit getPlayerUnit() {
        return playerUnit;
    }

    public void setPlayerUnit(PlayerUnit playerUnit) {
        this.playerUnit = playerUnit;
    }
}

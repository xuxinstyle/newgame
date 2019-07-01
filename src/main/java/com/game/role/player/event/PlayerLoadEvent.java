package com.game.role.player.event;

import com.event.event.IEvent;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/1 15:20
 */
public class PlayerLoadEvent implements IEvent {

    PlayerEnt playerEnt;

    public static PlayerLoadEvent valueOf(PlayerEnt playerEnt){
        PlayerLoadEvent event = new PlayerLoadEvent();
        event.setPlayerEnt(playerEnt);
        return event;
    }
    public PlayerEnt getPlayerEnt() {
        return playerEnt;
    }

    public void setPlayerEnt(PlayerEnt playerEnt) {
        this.playerEnt = playerEnt;
    }

    @Override
    public long getId() {
        return 1;
    }
}

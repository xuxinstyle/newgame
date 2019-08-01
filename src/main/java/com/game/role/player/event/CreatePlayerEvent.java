package com.game.role.player.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 18:26
 */
public class CreatePlayerEvent implements IEvent {

    private Player player;

    public static CreatePlayerEvent valueOf(Player player) {
        CreatePlayerEvent event = new CreatePlayerEvent();
        event.setPlayer(player);
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public long getId() {
        return 0;
    }
}

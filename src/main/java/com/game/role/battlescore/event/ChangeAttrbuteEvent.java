package com.game.role.battlescore.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 15:58
 */
public class ChangeAttrbuteEvent implements IEvent {

    private Player player;

    public static ChangeAttrbuteEvent valueOf(Player player) {
        ChangeAttrbuteEvent event = new ChangeAttrbuteEvent();
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

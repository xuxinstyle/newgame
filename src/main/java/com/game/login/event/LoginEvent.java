package com.game.login.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 15:06
 */
public class LoginEvent implements IEvent {
    /**
     * 账号Id
     */
    private Player player;

    public static LoginEvent valueOf(Player player) {
        LoginEvent event = new LoginEvent();
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

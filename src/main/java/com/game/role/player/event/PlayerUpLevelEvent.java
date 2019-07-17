package com.game.role.player.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 19:31
 */
public class PlayerUpLevelEvent implements IEvent {

    /**
     * 玩家
     */
    private Player player;

    public static PlayerUpLevelEvent valueOf(Player player){
        PlayerUpLevelEvent event = new PlayerUpLevelEvent();
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
        return player.getObjectId()%2;
    }
}

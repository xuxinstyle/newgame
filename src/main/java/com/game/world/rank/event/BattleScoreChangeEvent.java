package com.game.world.rank.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 15:56
 */
public class BattleScoreChangeEvent implements IEvent {

    private Player player;

    public static BattleScoreChangeEvent valueOf(Player player) {
        BattleScoreChangeEvent event = new BattleScoreChangeEvent();
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

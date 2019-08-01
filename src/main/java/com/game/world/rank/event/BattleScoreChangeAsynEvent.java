package com.game.world.rank.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;
import com.game.util.CommonUtil;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 15:56
 */
public class BattleScoreChangeAsynEvent implements IEvent {

    private Player player;

    public static BattleScoreChangeAsynEvent valueOf(Player player) {
        BattleScoreChangeAsynEvent event = new BattleScoreChangeAsynEvent();
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
        return CommonUtil.ASYN_EVENT_ZERO;
    }
}

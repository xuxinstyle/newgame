package com.game.user.task.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 16:39
 */
public class TalkEvent implements IEvent {

    private Player player;
    /**
     * 玩家聊天的npcid
     */
    private int npcId;

    public static TalkEvent valueOf(Player player, int npcId) {
        TalkEvent event = new TalkEvent();
        event.setPlayer(player);
        event.setNpcId(npcId);
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    @Override
    public long getId() {
        return 0;
    }
}

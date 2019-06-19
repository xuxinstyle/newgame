package com.game.role.player.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 19:31
 */
public class PlayerUpLevelEvent implements IEvent {
    /**
     * 地图
     */
    private int mapId;
    /**
     * 玩家
     */
    private Player player;
    public static PlayerUpLevelEvent valueOf(int mapId, Player player){
        PlayerUpLevelEvent event = new PlayerUpLevelEvent();
        event.setMapId(mapId);
        event.setPlayer(player);
        return event;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

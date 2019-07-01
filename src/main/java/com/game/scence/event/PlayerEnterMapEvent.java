package com.game.scence.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:06
 */
public class PlayerEnterMapEvent implements IEvent {
    private Player player;
    /**
     * 当前地图id
     */
    private int currMapId;
    /**
     * 目标地图id
     */
    private int targeetMapId;

    public static PlayerEnterMapEvent valueOf(Player player, int currMapId, int targeetMapId){
        PlayerEnterMapEvent event = new PlayerEnterMapEvent();
        event.setCurrMapId(currMapId);
        event.setTargeetMapId(targeetMapId);
        event.setPlayer(player);
        return event;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCurrMapId() {
        return currMapId;
    }

    public void setCurrMapId(int currMapId) {
        this.currMapId = currMapId;
    }

    public int getTargeetMapId() {
        return targeetMapId;
    }

    public void setTargeetMapId(int targeetMapId) {
        this.targeetMapId = targeetMapId;
    }

    @Override
    public long getId() {
        return player.getObjectId()%2;
    }
}

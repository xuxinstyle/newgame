package com.game.scence.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;
import com.game.scence.model.PlayerPosition;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:07
 */
public class PlayerMoveEvent implements IEvent {

    private int mapId;

    private Player player;
    /**
     * 当前位置
     */
    private PlayerPosition currPosition;
    /**
     * 目标位置
     */
    private PlayerPosition targetPosition;
    public static PlayerMoveEvent valueOf(int mapId,Player player, PlayerPosition currPosition, PlayerPosition targetPosition ){
        PlayerMoveEvent event = new PlayerMoveEvent();
        event.setCurrPosition(currPosition);
        event.setTargetPosition(targetPosition);
        event.setPlayer(player);
        event.setMapId(mapId);
        return event;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public PlayerPosition getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(PlayerPosition currPosition) {
        this.currPosition = currPosition;
    }

    public PlayerPosition getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(PlayerPosition targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

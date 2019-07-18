package com.game.scence.visible.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;
import com.game.scence.visible.model.Position;

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
    private Position currPosition;
    /**
     * 目标位置
     */
    private Position targetPosition;

    public static PlayerMoveEvent valueOf(int mapId, Player player, Position currPosition, Position targetPosition ){
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

    public Position getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(Position currPosition) {
        this.currPosition = currPosition;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
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

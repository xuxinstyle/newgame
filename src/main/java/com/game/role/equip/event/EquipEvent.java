package com.game.role.equip.event;

import com.event.event.IEvent;
import com.game.role.player.model.Player;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 20:44
 */
public class EquipEvent implements IEvent {
    /**
     * 穿的装备
     */
    private Equipment equipment;
    /**
     * 角色唯一id
     */
    private long playerId;

    public static EquipEvent valueOf(Equipment equipment, long playerId){
        EquipEvent event = new EquipEvent();
        event.setEquipment(equipment);
        event.setPlayerId(playerId);
        return event;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public long getId() {
        return playerId%2;
    }
}

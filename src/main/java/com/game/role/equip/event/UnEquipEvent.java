package com.game.role.equip.event;

import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 20:46
 */
public class UnEquipEvent {
    /**
     * 脱的装备
     */
    private Equipment item;
    /**
     * 角色id
     */
    private long playerId;

    public Equipment getItem() {
        return item;
    }

    public void setItem(Equipment item) {
        this.item = item;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}

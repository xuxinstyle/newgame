package com.game.user.equipupgrade.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 14:47
 */
public class CM_EquipUpgrade {
    /**
     * 道具唯一id
     */
    private long equipObjectId;

    public long getEquipObjectId() {
        return equipObjectId;
    }

    public void setEquipObjectId(long equipObjectId) {
        this.equipObjectId = equipObjectId;
    }
}

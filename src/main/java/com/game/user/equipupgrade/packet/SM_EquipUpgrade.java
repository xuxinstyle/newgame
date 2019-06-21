package com.game.user.equipupgrade.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 14:47
 */
public class SM_EquipUpgrade {
    /**
     * 1 成功 2 背包中没有该道具 3 道具不是装备类型 4 升级道具不足 5 达到升级上限
     */
    private int status;
    /**
     * 装备名称
     */
    private String equipName;

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

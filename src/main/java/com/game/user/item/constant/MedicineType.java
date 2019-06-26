package com.game.user.item.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/26 14:19
 */
public enum  MedicineType {
    /**
     * 增加最大血量的药
     */
    HP_MEDICINE(12),
    /**
     * 增加最大蓝量的药
     */
    MP_MEDICINE(13),
    ;
    MedicineType(int itemModelId){
        this.itemModelId = itemModelId;
    }
    private int itemModelId;

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
}

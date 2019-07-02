package com.game.user.itemeffect.model;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 11:51
 */
public class ItemEffectdetaiInfo {
    /**
     * 道具id
     */
    private int itemModelId;
    /**
     * 是否有效 true 有效 ，false 无效
     */
    private boolean effectiveStatus;
    /**
     * 失效时间点
     */
    private long invalidTime;

    public static ItemEffectdetaiInfo valueOf(int itemModelId, boolean effective, long invalidTime){
        ItemEffectdetaiInfo itemEffectdetaiInfo = new ItemEffectdetaiInfo();
        itemEffectdetaiInfo.setItemModelId(itemModelId);
        itemEffectdetaiInfo.setEffectiveStatus(effective);
        itemEffectdetaiInfo.setInvalidTime(invalidTime);
        return itemEffectdetaiInfo;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }

    public boolean isEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(boolean effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public long getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(long invalidTime) {
        this.invalidTime = invalidTime;
    }
}

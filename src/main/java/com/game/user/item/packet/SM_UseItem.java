package com.game.user.item.packet;

/**
 * 使用道具状态       注意：暂时只有药品的实验 其他道具的使用，等后续加需求
 * @Author：xuxin
 * @Date: 2019/6/21 9:19
 */
public class SM_UseItem {
    /**
     * 使用状态 1 成功 2 没有道具 3 道具不能使用
     */
    private int status;
    /**
     * 有效时长
     */
    private long effectiveTime;

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

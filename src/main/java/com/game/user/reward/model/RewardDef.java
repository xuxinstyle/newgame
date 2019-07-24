package com.game.user.reward.model;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 10:07
 * 配置奖励
 */
public class RewardDef {
    // 道具id
    private int itemId;
    // 道具数量
    private int num;

    public static RewardDef valueOf(int itemId, int num) {
        RewardDef def = new RewardDef();
        def.setItemId(itemId);
        def.setNum(num);
        return def;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

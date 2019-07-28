package com.game.user.task.constant;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:42
 */
public enum TaskStatus {
    /**
     * 未接受 需要触发
     */
    UN_ACCEPT(1),
    /**
     * 已接受 已经被触发 但是没有完成
     */
    ACCEPT(2),
    /**
     * 已完成 已经完成但是没有领取奖励
     */
    FINISH(3),
    /**
     * 已完成 并且已经领取了奖励
     */
    FINISH_REWARD(4),;
    private int id;

    TaskStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

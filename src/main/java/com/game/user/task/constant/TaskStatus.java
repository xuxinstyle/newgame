package com.game.user.task.constant;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:42
 */
public enum TaskStatus {
    /**
     * 未接受
     */
    UN_ACCEPT(1),
    /**
     * 已接受
     */
    ACCEPT(2),
    /**
     * 已完成
     */
    FINSH(3),;
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

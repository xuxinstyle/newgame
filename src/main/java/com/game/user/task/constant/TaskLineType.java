package com.game.user.task.constant;

import com.game.SpringContext;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:31
 */
public enum TaskLineType {
    /**
     * 主线任务
     */
    MAIN_TACK(1),
    /**
     * 1号支线任务
     */
    BRANCH_LINE_TASK(2),
    /**
     * 每日任务
     */
    DAILY_TASK(3),;

    private int id;


    TaskLineType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

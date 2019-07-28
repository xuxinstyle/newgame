package com.game.user.task.resource;

import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 15:11
 */
@LoadResource
public class TaskLineResource {
    /**
     * 任务线类型
     */
    private int id;
    /**
     * 任务集合
     */
    private int[] taskIds;

    /**
     * 初始任务
     */
    private int initTaskId;

    public int getInitTaskId() {
        return initTaskId;
    }

    public void setInitTaskId(int initTaskId) {
        this.initTaskId = initTaskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(int[] taskIds) {
        this.taskIds = taskIds;
    }
}

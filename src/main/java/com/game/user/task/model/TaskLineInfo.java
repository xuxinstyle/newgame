package com.game.user.task.model;

import com.game.SpringContext;
import com.game.user.task.resource.TaskLineResource;
import com.game.user.task.resource.TaskResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 14:43
 */
public class TaskLineInfo {
    /**
     * 线路编号
     */
    private int lineId;
    /**
     * 该条线路正在执行的任务
     */
    private Task currTask;

    /**
     * 初始化线路任务
     * @param lineId
     * @return
     */
    public static TaskLineInfo valueOf(int lineId) {
        TaskLineResource taskLineResource = SpringContext.getTaskService().getTaskLineResource(lineId);
        if (taskLineResource == null) {
            return null;
        }
        TaskLineInfo taskLineInfo = new TaskLineInfo();
        taskLineInfo.setLineId(lineId);
        return taskLineInfo;
    }


    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public Task getCurrTask() {
        return currTask;
    }

    public void setCurrTask(Task currTask) {
        this.currTask = currTask;
    }

    public Task openTask(int taskId) {
        Task nextTask = Task.valueOf(taskId);
        this.currTask = nextTask;
        return nextTask;
    }

    public boolean haveNextTask() {
        int taskId = currTask.getTaskId();
        TaskResource taskResource = SpringContext.getTaskService().getTaskResource(taskId);
        int[] nextTaskIds = taskResource.getNextTaskIds();
        if (nextTaskIds == null) {
            return false;
        }
        return true;
    }
}

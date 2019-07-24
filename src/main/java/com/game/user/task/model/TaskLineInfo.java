package com.game.user.task.model;

import com.game.user.task.constant.TaskStatus;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:29
 */
public class TaskLineInfo {
    /**
     * 当前正在执行的任务id
     */
    private int currTaskId;
    /**
     * 任务进度
     */
    private int progress;
    /**
     * 任务状态
     */
    private int status;

    public static TaskLineInfo valueOf(int taskId) {
        TaskLineInfo taskLineInfo = new TaskLineInfo();
        taskLineInfo.setCurrTaskId(taskId);
        // 这个进度应该跟配置表有关
        taskLineInfo.setProgress(0);
        taskLineInfo.setStatus(TaskStatus.UN_ACCEPT.getId());
        return taskLineInfo;
    }

    public void acceptTask() {
        this.status = TaskStatus.ACCEPT.getId();
    }

    public void finshTask() {
        this.status = TaskStatus.FINSH.getId();
    }

    public int getCurrTaskId() {
        return currTaskId;
    }

    public void setCurrTaskId(int currTaskId) {
        this.currTaskId = currTaskId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

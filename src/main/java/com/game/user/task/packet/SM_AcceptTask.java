package com.game.user.task.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 14:32
 */
public class SM_AcceptTask {
    /**
     * 1 成功 0 失败
     */
    private int status;
    /**
     * 任务id
     */
    private int taskId;

    public static SM_AcceptTask valueOf(int status) {
        SM_AcceptTask sm = new SM_AcceptTask();
        sm.setStatus(status);
        return sm;
    }

    public int getStatus() {
        return status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

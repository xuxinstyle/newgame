package com.game.user.task.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 21:48
 */
public class SM_TaskFinished {
    /**
     * 任务id
     */
    private int taskId;

    public static SM_TaskFinished valueOf(int taskId) {
        SM_TaskFinished sm = new SM_TaskFinished();
        sm.setTaskId(taskId);
        return sm;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}

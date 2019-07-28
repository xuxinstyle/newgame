package com.game.user.task.packet.bean;

import com.game.user.task.model.Task;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 15:47
 */
public class TaskVO {
    /**
     * 任务id
     */
    private int taskId;
    /**
     * 任务进度
     */
    private int[] processor;

    public static TaskVO valueOf(Task currTask) {
        TaskVO taskVO = new TaskVO();
        taskVO.setTaskId(currTask.getTaskId());
        taskVO.setProcessor(currTask.getExcuteProgress());
        return taskVO;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int[] getProcessor() {
        return processor;
    }

    public void setProcessor(int[] processor) {
        this.processor = processor;
    }
}

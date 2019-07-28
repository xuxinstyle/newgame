package com.game.user.task.model;

import com.game.SpringContext;
import com.game.user.task.resource.TaskResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 10:50
 */
public class Task {
    /**
     * 任务id
     */
    private int taskId;
    /**
     * 任务执行进度 初始值未0 每次 表示0-1 0-8
     */
    private int[] excuteProgress;
    /**
     * 任务触发进度
     */
    private int[] triggerProgress;

    public static Task valueOf(int taskId) {
        Task task = new Task();
        TaskResource taskResource = SpringContext.getTaskService().getTaskResource(taskId);
        TaskConDef[] finishConDefs = taskResource.getFinishConDefs();
        TaskConDef[] triggerConDefs = taskResource.getTriggerConDefs();
        task.setExcuteProgress(new int[finishConDefs.length]);
        if (triggerConDefs != null) {
            task.setTriggerProgress(new int[triggerConDefs.length]);
        }
        task.setTaskId(taskId);

        return task;
    }

    /**
     * 修改任务完成进度值
     * 这里先只做进度值 增加
     *
     * @return
     */
    public boolean changeExcuteProgress(int index, int value, boolean replace) {
        if (value < 0) {
            return false;
        }
        if (index + 1 > excuteProgress.length) {
            return false;
        }
        // 当前进度
        int currValue = this.excuteProgress[index];
        if (replace) {
            if (value == currValue) {
                return false;
            }
            excuteProgress[index] = value;
        } else {
            // 再原来的基础增加进度值
            if (Integer.MAX_VALUE - currValue <= value) {
                excuteProgress[index] = Integer.MAX_VALUE;
            } else {
                excuteProgress[index] = currValue + value;
            }
        }
        return true;
    }

    /**
     * 修改任务触发进度值
     * 这里先只做增加
     *
     * @return
     */
    public boolean changeTriggerProgress(int index, int value) {
        if (value < 0) {
            return false;
        }
        if (index + 1 > triggerProgress.length) {
            return false;
        }
        int currValue = this.triggerProgress[index];
        // 再原来的基础增加进度值
        if (Integer.MAX_VALUE - currValue <= value) {
            triggerProgress[index] = Integer.MAX_VALUE;
        } else {
            triggerProgress[index] = currValue + value;
        }
        return true;

    }

    public int getTaskId() {
        return taskId;
    }


    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int[] getExcuteProgress() {
        return excuteProgress;
    }

    public void setExcuteProgress(int[] excuteProgress) {
        this.excuteProgress = excuteProgress;
    }

    public int[] getTriggerProgress() {
        return triggerProgress;
    }

    public void setTriggerProgress(int[] triggerProgress) {
        this.triggerProgress = triggerProgress;
    }
}

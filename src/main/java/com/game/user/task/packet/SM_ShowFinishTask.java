package com.game.user.task.packet;

import java.util.List;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 23:55
 */
public class SM_ShowFinishTask {
    /**
     * 已经完成的任务
     */
    private Set<Integer> finishedTask;

    public Set<Integer> getFinishedTask() {
        return finishedTask;
    }

    public void setFinishedTask(Set<Integer> finishedTask) {
        this.finishedTask = finishedTask;
    }
}

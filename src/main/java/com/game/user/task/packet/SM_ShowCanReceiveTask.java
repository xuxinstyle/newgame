package com.game.user.task.packet;

import java.util.List;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 14:06
 */
public class SM_ShowCanReceiveTask {
    /**
     * 可领取的任务列表
     */
    private Set<Integer> canReceiveTasks;

    public Set<Integer> getCanReceiveTasks() {
        return canReceiveTasks;
    }

    public void setCanReceiveTasks(Set<Integer> canReceiveTasks) {
        this.canReceiveTasks = canReceiveTasks;
    }
}

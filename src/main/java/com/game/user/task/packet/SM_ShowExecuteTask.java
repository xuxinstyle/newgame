package com.game.user.task.packet;

import com.game.user.task.packet.bean.TaskVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 15:28
 */
public class SM_ShowExecuteTask {
    /**
     * 正在执行的任务
     */
    private List<TaskVO> executeTasks;

    public List<TaskVO> getExecuteTasks() {
        return executeTasks;
    }

    public void setExecuteTasks(List<TaskVO> executeTasks) {
        this.executeTasks = executeTasks;
    }
}

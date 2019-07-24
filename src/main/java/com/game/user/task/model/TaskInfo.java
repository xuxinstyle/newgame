package com.game.user.task.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:18
 */
public class TaskInfo {
    /**
     * 任务类型id,任务详细信息 todo:每日任务（每天刷新，且不用接受，多个任务可同时触发的）怎么办
     */
    Map<Integer, TaskLineInfo> taskMap = new HashMap<>();

    public static TaskInfo valueOf() {
        TaskInfo taskInfo = new TaskInfo();
        Map<Integer, TaskLineInfo> taskMap = new HashMap<>();
        taskInfo.setTaskMap(taskMap);
        return taskInfo;
    }

    public void addTaskLine(int taskLineType, TaskLineInfo taskLineInfo) {
        taskMap.put(taskLineType, taskLineInfo);
    }

    public Map<Integer, TaskLineInfo> getTaskMap() {
        return taskMap;
    }

    public void setTaskMap(Map<Integer, TaskLineInfo> taskMap) {
        this.taskMap = taskMap;
    }
}

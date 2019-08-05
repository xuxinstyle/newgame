package com.game.user.task.model;

import com.game.SpringContext;
import com.game.user.task.constant.TaskLineType;
import com.game.user.task.resource.TaskLineResource;
import com.game.user.task.resource.TaskResource;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:18
 */
public class TaskInfo {
    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(TaskInfo.class);
    /**
     * 正在进行的 任务线路信息
     */
    private Map<Integer, TaskLineInfo> taskLineInfoMap = new HashMap<>();


    /**
     * 已经完成的任务id 用来领取奖励
     */
    private Set<Integer> finishedTasks = new HashSet<>();

    /**
     * 可领取的任务列表
     */
    private Set<Integer> canAcceptTasks = new HashSet<>();


    public void finishTask(int taskId) {
        if (!finishedTasks.contains(taskId)) {
            finishedTasks.add(taskId);
        }
    }

    public void addCanReceiveTask(int taskId) {
        canAcceptTasks.add(taskId);
    }


    public static TaskInfo valueOf() {
        TaskInfo taskInfo = new TaskInfo();
        // 初始化主线任务
        int mainLineId = TaskLineType.MAIN_TACK.getId();

        //将任务放在可领取的任务列表中
        TaskLineResource mainLineResource = SpringContext.getTaskService().getTaskLineResource(mainLineId);
        int initTaskId = mainLineResource.getInitTaskId();
        taskInfo.addCanReceiveTask(initTaskId);


        return taskInfo;
    }


    public Set<Integer> getFinishedTasks() {
        return finishedTasks;
    }

    public void setFinishedTasks(Set<Integer> finishedTasks) {
        this.finishedTasks = finishedTasks;
    }

    public Set<Integer> getCanAcceptTasks() {
        return canAcceptTasks;
    }

    public void setCanAcceptTasks(Set<Integer> canAcceptTasks) {
        this.canAcceptTasks = canAcceptTasks;
    }

    public Map<Integer, TaskLineInfo> getTaskLineInfoMap() {
        return taskLineInfoMap;
    }

    public void setTaskLineInfoMap(Map<Integer, TaskLineInfo> taskLineInfoMap) {
        this.taskLineInfoMap = taskLineInfoMap;
    }
}

package com.game.user.task.resource;

import com.game.user.reward.model.RewardDef;
import com.game.user.task.constant.TaskConditionType;
import com.game.user.task.model.TaskConDef;
import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 11:31
 */
@LoadResource
public class TaskResource {
    /**
     * 任务id
     */
    private int id;
    /**
     * 任务线编号
     */
    private int lineId;
    /**
     * 下一个任务
     */
    private int[] nextTaskIds;
    /**
     * 是否是初始任务
     */
    private boolean isInitTask;
    /**
     * 任务触发条件
     */
    private TaskConDef[] triggerConDefs;
    /**
     * 任务完成条件
     */
    private TaskConDef[] finishConDefs;
    /**
     * 任务完成奖励
     */
    private RewardDef[] rewardDefs;
    /**
     * 描述
     */
    private String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isExecutEventType(TaskConditionType type) {
        if (finishConDefs == null || finishConDefs.length == 0) {
            return false;
        }
        for (int i = 0; i < this.finishConDefs.length; i++) {
            if (this.finishConDefs[i].getType() == type) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int[] getNextTaskIds() {
        return nextTaskIds;
    }

    public void setNextTaskIds(int[] nextTaskIds) {
        this.nextTaskIds = nextTaskIds;
    }

    public boolean isInitTask() {
        return isInitTask;
    }

    public void setInitTask(boolean initTask) {
        isInitTask = initTask;
    }

    public TaskConDef[] getTriggerConDefs() {
        return triggerConDefs;
    }

    public void setTriggerConDefs(TaskConDef[] triggerConDefs) {
        this.triggerConDefs = triggerConDefs;
    }

    public TaskConDef[] getFinishConDefs() {
        return finishConDefs;
    }

    public void setFinishConDefs(TaskConDef[] finishConDefs) {
        this.finishConDefs = finishConDefs;
    }

    public RewardDef[] getRewardDefs() {
        return rewardDefs;
    }

    public void setRewardDefs(RewardDef[] rewardDefs) {
        this.rewardDefs = rewardDefs;
    }
}

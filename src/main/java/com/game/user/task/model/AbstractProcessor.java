package com.game.user.task.model;

import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务事件处理器
 *
 * @Author：xuxin
 * @Date: 2019/7/25 16:20
 */
public abstract class AbstractProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);

    /**
     * 任务事件处理器 集合
     */
    private static final Map<TaskConditionType, AbstractProcessor> processorMap = new HashMap<>();

    @PostConstruct
    private final void init() {
        processorMap.put(getTaskConditionType(), this);
    }

    /**
     * 获取任务处理器的任务条件类型
     *
     * @return
     */
    protected abstract TaskConditionType getTaskConditionType();

    public static AbstractProcessor getProcessor(TaskConditionType taskConditionType) {
        return processorMap.get(taskConditionType);
    }

    /**
     * 刷新 任务触发进度
     *
     * @param triggerConditions
     * @param task
     * @param type
     * @return
     */
    public boolean refreshTrigger(TaskConDef[] triggerConditions, Task task, TaskConditionType type, String key) {

        // 任务进度是否有变化
        boolean change = false;

        // 遍历任务触发条件
        for (int i = 0; i < triggerConditions.length; i++) {
            TaskConDef conDef = triggerConditions[i];
            // 验证 任务 条件
            if (this.checkTaskCon(type, conDef, key)) {
                // 获取任务改变的值
                int value = this.getValue(type, conDef);
                change = change || task.changeTriggerProgress(i, value);
            }
        }
        return change;

    }

    public abstract boolean initProgress(Task task, Player player, int index, TaskConDef def);

    /**
     * 刷新 任务执行进度
     *
     * @param excutCondDefs
     * @param task
     * @param type
     * @param key           此次事件的key 即npcid 或怪物id
     * @return
     */
    public boolean refreshExecute(TaskConDef[] excutCondDefs, Task task, TaskConditionType type, String key) {
        // 任务进度是否有变化
        boolean change = false;

        // 遍历任务完成条件
        for (int i = 0; i < excutCondDefs.length; i++) {
            TaskConDef conDef = excutCondDefs[i];
            // 验证 任务 条件
            if (this.checkTaskCon(type, conDef, key)) {
                // 获取任务改变的值
                int value = getValue(type, conDef);
                change = change || task.changeExcuteProgress(i, value, isReplace());
                logger.info("任务[{}]值改变了[{}]", task.getTaskId(), value);
            }
        }
        return change;
    }

    protected abstract boolean isReplace();

    /**
     * 获取当前这个事件改变的进度值
     *
     * @param type
     * @param conDef
     */
    protected abstract int getValue(TaskConditionType type, TaskConDef conDef);

    /**
     * 验证当前事件是否是配置中的条件事件
     *
     * @param type
     * @param conDef
     * @param key
     * @return
     */
    protected abstract boolean checkTaskCon(TaskConditionType type, TaskConDef conDef, String key);

}

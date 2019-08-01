package com.game.user.task.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.role.player.model.Player;
import com.game.scence.npc.resource.NpcResource;
import com.game.scence.visible.resource.MapResource;
import com.game.user.task.constant.TaskConditionType;
import com.game.user.task.constant.TaskLineType;
import com.game.user.task.entity.TaskEnt;
import com.game.user.task.event.TalkEvent;
import com.game.user.task.model.*;
import com.game.user.task.packet.*;
import com.game.user.task.packet.bean.TaskVO;
import com.game.user.task.resource.TaskLineResource;
import com.game.user.task.resource.TaskResource;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:55
 */
@Component
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private EntityCacheService<String, TaskEnt> taskCacheService;

    public TaskEnt getTaskEnt(String accountId) {
        return taskCacheService.findOrCreate(TaskEnt.class, accountId, new EntityBuilder<String, TaskEnt>() {
            @Override
            public TaskEnt newInstance(String id) {
                return TaskEnt.valueOf(id);
            }
        });
    }

    public void save(TaskEnt taskEnt) {
        taskCacheService.saveOrUpdate(taskEnt);
    }

    @Override
    public TaskResource getTaskResource(int taskId) {
        return taskManager.getTaskResource(taskId);
    }

    @Override
    public TaskLineResource getTaskLineResource(int lineType) {
        return taskManager.getTaskLineResource(lineType);
    }

    /**
     * @param player
     */
    @Override
    public void doAfterLogin(Player player) {
        doHandleTask(player, TaskConditionType.ACCOUNT_LOGIN, null);
    }

    @Override
    public void doKillMonster(String accountId, int mapId, int monsterId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        doHandleTask(player, TaskConditionType.KILL_MONSTER, monsterId + "");
    }

    @Override
    public void doPassMapTask(String accountId, int mapId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        doHandleTask(player, TaskConditionType.PASS_MAP, mapId + "");
    }

    @Override
    public void doEnterMapTask(String accountId, int mapId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        doHandleTask(player, TaskConditionType.ENTER_MAP, mapId + "");
    }

    @Override
    public void doPlayerLevelUp(Player player) {
        doHandleTask(player, TaskConditionType.PLAYER_LEVEL_UP, "1");
    }

    @Override
    public void doTalkWithNpc(Player player, int npcId) {
        doHandleTask(player, TaskConditionType.NPC_TALK, npcId + "");
    }

    private void doHandleTask(Player player, TaskConditionType type, String key) {
        TaskEnt taskEnt = getTaskEnt(player.getAccountId());
        TaskInfo taskInfo = taskEnt.getTaskInfo();


        // 获取正在进行的任务集合
        List<Task> excuteTask = getExcuteTask(taskInfo, type);
        // 检查是否有任务进度变化
        AbstractProcessor processor = AbstractProcessor.getProcessor(type);
        if (processor == null) {
            logger.warn("没有事件[{}]的处理器", type.name());
            return;
        }
        for (Task task : excuteTask) {
            TaskResource taskResource = getTaskResource(task.getTaskId());
            if (processor.refreshExecute(taskResource.getFinishConDefs(), task, type, key)) {
                doAfterTaskChange(task, player);
            }
        }
    }
    /**
     * 任务进度发生改变
     * @param task
     * @param player
     */
    private void doAfterTaskChange(Task task, Player player) {
        // 验证任务是否可以触发完成
        int taskId = task.getTaskId();
        TaskResource taskResource = getTaskResource(taskId);
        // 可以完成 修改任务的状态
        if (canFinish(task, taskResource)) {
            TaskEnt taskEnt = getTaskEnt(player.getAccountId());
            TaskInfo taskInfo = taskEnt.getTaskInfo();
            // 将已经完成的任务放在 已经完成任务的集合中
            taskInfo.finishTask(taskId);
            save(taskEnt);
            // 通知客户端
            logger.info("玩家：[{}],任务[{}]完成", player.getAccountId(), task.getTaskId());
            SendPacketUtil.send(player, SM_TaskFinished.valueOf(taskId));
        }


    }


    private boolean canFinish(Task task, TaskResource taskResource) {
        int[] excuteProgress = task.getExcuteProgress();
        TaskConDef[] finishConDefs = taskResource.getFinishConDefs();
        for (int i = 0; i < finishConDefs.length; i++) {
            String[] values = finishConDefs[i].getValues();
            int finishValue = Integer.parseInt(values[0]);
            if (excuteProgress[i] < finishValue) {
                return false;
            }
        }
        return true;

    }


    private List<Task> getExcuteTask(TaskInfo taskInfo, TaskConditionType type) {
        List<Task> taskList = new ArrayList<>();
        // 遍历线路任务
        for (TaskLineInfo taskLineInfo : taskInfo.getTaskLineInfoMap().values()) {
            Task currTask = taskLineInfo.getCurrTask();
            if (currTask == null) {
                continue;
            }
            int taskId = currTask.getTaskId();
            TaskResource taskResource = getTaskResource(taskId);
            if (taskResource.isExecutEventType(type)) {
                taskList.add(taskLineInfo.getCurrTask());
            }
        }
        // 遍历每日任务
        for (Task task : taskInfo.getDailyTaskMap().values()) {
            int taskId = task.getTaskId();
            TaskResource taskResource = getTaskResource(taskId);
            if (taskResource.isExecutEventType(type)) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    @Override
    public void acceptTask(String accountId, int taskId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        TaskEnt taskEnt = getTaskEnt(accountId);
        TaskInfo taskInfo = taskEnt.getTaskInfo();
        Set<Integer> canAcceptTasks = taskInfo.getCanAcceptTasks();
        // 检查
        if (!canAcceptTasks.contains(new Integer(taskId))) {
            // 任务不可接受
            RequestException.throwException(I18nId.NOT_ACCEPT_TASK);
        }

        // 接受任务
        TaskResource taskResource = SpringContext.getTaskService().getTaskResource(taskId);
        int lineId = taskResource.getLineId();
        TaskLineInfo taskLineInfo = taskInfo.getTaskLineInfoMap().get(lineId);
        //  没有开启支线 开支线
        if (taskLineInfo == null && taskResource.isInitTask()) {
            //开启支线任务
            taskLineInfo = TaskLineInfo.valueOf(lineId);
            taskInfo.getTaskLineInfoMap().put(lineId, taskLineInfo);
            logger.info("开启新的任务线[{}]", lineId);
        }
        // 初始化任务
        Task task = taskLineInfo.openTask(taskId);
        if (initProgress(player, taskResource, task)) {
            doAfterTaskChange(task, player);
        }
        // 从可领取任务列表移除
        canAcceptTasks.remove(new Integer(taskId));
        //保存
        save(taskEnt);
        SM_AcceptTask sm = SM_AcceptTask.valueOf(1);
        sm.setTaskId(taskId);
        // 通知客户端
        SendPacketUtil.send(accountId, sm);

    }

    private boolean initProgress(Player player, TaskResource taskResource, Task task) {
        boolean change = false;
        TaskConDef[] finishConDefs = taskResource.getFinishConDefs();
        if (finishConDefs == null || finishConDefs.length <= 0) {
            return change;
        }
        for (int i = 0; i < finishConDefs.length; i++) {
            AbstractProcessor processor = AbstractProcessor.getProcessor(finishConDefs[i].getType());
            change = processor.initProgress(task, player, i, finishConDefs[i]);
        }
        return change;
    }

    @Override
    public void showFinishTask(String accountId) {
        TaskEnt taskEnt = getTaskEnt(accountId);
        TaskInfo taskInfo = taskEnt.getTaskInfo();
        Set<Integer> finishedTask = taskInfo.getFinishedTasks();
        SM_ShowFinishTask sm = new SM_ShowFinishTask();
        sm.setFinishedTask(finishedTask);
        SendPacketUtil.send(accountId, sm);
    }

    @Override
    public void receiveTaskAward(String accountId, int taskId) {
        // check 发来的参数是否正确
        TaskResource taskResource = getTaskResource(taskId);
        if (taskResource == null) {
            RequestException.throwException(I18nId.NOT_TASK);
        }
        TaskEnt taskEnt = getTaskEnt(accountId);
        TaskInfo taskInfo = taskEnt.getTaskInfo();
        // 发将
        Set<Integer> finishedTask = taskInfo.getFinishedTasks();
        // 没有在完成任务的列表
        if (!finishedTask.contains(taskId)) {
            logger.info("任务[{}]没有完成,无法领奖", taskId);
            RequestException.throwException(I18nId.RECEIVE_REWARD_ERROR);
        }
        // 移除list中的数据
        finishedTask.remove(new Integer(taskId));

        // 发奖
        SpringContext.getRewardService().doReward(accountId, taskResource.getRewardDefs());
        logger.info("玩家成功[{}]领取任务[{}]奖励", accountId, taskId);

        // 移除当前任务
        // 每日任务
        int lineId = taskResource.getLineId();
        if (lineId == TaskLineType.DAILY_TASK.getId()) {
            Map<Integer, Task> dailyTaskMap = taskInfo.getDailyTaskMap();
            Task task = dailyTaskMap.get(taskId);
            if (task != null) {
                dailyTaskMap.remove(taskId);
                logger.info("每日任务[{}]领取完成，从任务列表移除", taskId);
            }
            // 每日任务不需要开启下一个任务
            save(taskEnt);
            return;
        }
        // 线路任务
        // 开启下组任务
        openNextTaskOrRemove(taskInfo, taskResource);
        // 领完奖后 开始下一个任务
        save(taskEnt);
    }

    public void openNextTaskOrRemove(TaskInfo taskInfo, TaskResource taskResource) {

        int lineId = taskResource.getLineId();
        Map<Integer, TaskLineInfo> taskLineInfoMap = taskInfo.getTaskLineInfoMap();
        TaskLineInfo taskLineInfo = taskLineInfoMap.get(lineId);
        // 开启下一个任务
        int[] nextTaskIds = taskResource.getNextTaskIds();
        // 如果是线路任务 查看该线任务是否结束 结束则移除任务线
        Task currTask = taskLineInfo.getCurrTask();
        if (currTask == null) {
            return;
        }
        if (nextTaskIds == null) {
            // 没有下一个任务 移除任务线
            taskLineInfoMap.remove(lineId);
            logger.info("任务线[{}]完结", lineId);
        } else {
            taskLineInfo.setCurrTask(null);
            for (int taskId : nextTaskIds) {
                logger.info("可以去领取任务[{}]了", taskId);
                // 将任务加入可领取任务列表
                taskInfo.getCanAcceptTasks().add(taskId);
            }
        }

    }

    /**
     * 查看了领取的任务列表
     *
     * @param accountId
     */
    @Override
    public void showCanReceiveTask(String accountId) {
        TaskEnt taskEnt = getTaskEnt(accountId);
        TaskInfo taskInfo = taskEnt.getTaskInfo();
        Set<Integer> canReceiveTasks = taskInfo.getCanAcceptTasks();
        SM_ShowCanReceiveTask sm = new SM_ShowCanReceiveTask();
        sm.setCanReceiveTasks(canReceiveTasks);
        SendPacketUtil.send(accountId, sm);
    }

    /**
     * 查看正在进行的任务  领取了但是没有完成的任务
     *
     * @param accountId
     */
    @Override
    public void showExecuteTasks(String accountId) {
        TaskEnt taskEnt = getTaskEnt(accountId);
        TaskInfo taskInfo = taskEnt.getTaskInfo();
        List<TaskVO> executeTasks = new ArrayList<>();
        // 线路任务
        Map<Integer, TaskLineInfo> taskLineInfoMap = taskInfo.getTaskLineInfoMap();
        for (TaskLineInfo taskLineInfo : taskLineInfoMap.values()) {
            Task currTask = taskLineInfo.getCurrTask();
            if (currTask != null) {
                executeTasks.add(TaskVO.valueOf(currTask));
            }
        }
        // 每日任务
        Map<Integer, Task> dailyTaskMap = taskInfo.getDailyTaskMap();
        for (Task task : dailyTaskMap.values()) {
            executeTasks.add(TaskVO.valueOf(task));
        }
        SM_ShowExecuteTask sm = new SM_ShowExecuteTask();
        sm.setExecuteTasks(executeTasks);
        SendPacketUtil.send(accountId, sm);
    }
}

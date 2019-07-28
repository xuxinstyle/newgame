package com.game.user.task.service;

import com.game.role.player.model.Player;
import com.game.user.task.resource.TaskLineResource;
import com.game.user.task.resource.TaskResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:54
 */
public interface TaskService {
    /**
     * 获取任务资源
     *
     * @param taskId
     * @return
     */
    TaskResource getTaskResource(int taskId);

    /**
     * 获取任务线路资源
     *
     * @param lineType
     * @return
     */
    TaskLineResource getTaskLineResource(int lineType);

    /**
     * 玩家登录后的处理
     *
     * @param player
     */
    void doAfterLogin(Player player);

    /**
     * 做玩家与npc聊天操作 抛聊天事件
     *
     * @param accountId
     * @param mapId
     * @param npcId
     */
    void talkWithNpc(String accountId, int mapId, int npcId);

    /**
     * 与npc聊天
     *
     * @param player
     * @param npcId
     */
    void doTalkWithNpc(Player player, int npcId);

    /**
     * 查看已经完成的任务
     *
     * @param accountId
     */
    void showFinishTask(String accountId);

    /**
     * 领取任务奖励
     *
     * @param accountId
     * @param taskId
     */
    void receiveTaskAward(String accountId, int taskId);

    /**
     * 查看可领取的任务
     *
     * @param accountId
     */
    void showCanReceiveTask(String accountId);

    /**
     * 接受任务
     *
     * @param accountId
     * @param taskId
     */
    void acceptTask(String accountId, int taskId);

    /**
     * 查看正在进行的任务
     *
     * @param accountId
     */
    void showExecuteTasks(String accountId);

    /**
     * @param accountId 谁杀死的
     * @param mapId     怪物死亡的地图id
     * @param monsterId 怪物id
     */
    void doKillMonster(String accountId, int mapId, int monsterId);

    /**
     * 接受玩家进入地图任务事件处理
     *
     * @param accountId
     * @param mapId
     */
    void doPassMapTask(String accountId, int mapId);

    /**
     * 进入指定地图
     *
     * @param accountId
     * @param mapId
     */
    void doEnterMapTask(String accountId, int mapId);

    /**
     * 做玩家升级的任务事件处理
     *
     * @param player
     */
    void doPlayerLevelUp(Player player);
}

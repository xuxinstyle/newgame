package com.game.user.task.constant;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 11:03
 */
public enum TaskConditionType {
    /**
     * 玩家登陆
     */
    ACCOUNT_LOGIN(1),
    /**
     * 任务完成
     */
    TASK_FINISH(2),
    /**
     * 穿指定装备
     */
    EQUIP(3),
    /**
     * 与指定NPC对话
     */
    NPC_TALK(4),
    /**
     * 通关指定地图
     */
    PASS_MAP(5),

    /**
     * 杀指定怪物
     */
    KILL_MONSTER(6),
    /**
     * 玩家等级条件
     */
    PLAYER_LEVEL_UP(7),
    /**
     * 进入指定地图
     */
    ENTER_MAP(8),;
    private int id;

    TaskConditionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

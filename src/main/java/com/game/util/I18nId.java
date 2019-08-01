package com.game.util;

/**
 * @Author：xuxin
 * @Date: 2019/7/17 17:29
 */
public interface I18nId {
    // 技能使用成功
    int SKILL_USE_SUCCESS = 10001;
    // 技能失效
    int SKILL_INVALID = 10002;
    //未知错误
    int ERROR = 10003;
    //切换地图失败
    int CHANGE_MAP_ERROE = 10004;
    // 已经在目标地图中 无法切换
    int IN_THE_MAP = 10005;
    //进入地图失败
    int ENTER_MAP_ERROR = 10006;
    // 目标已死亡
    int TARGET_DEAD = 10007;
    // 地图中没有目标
    int MAP_NOT_TARGET = 10008;
    // 目标太远，无法施法
    int TARGET_TO_LONG = 10009;
    // 该地图不能释放技能
    int MAP_NOT_USE_SKILL = 10010;
    // 你已经死亡了无法施法技能
    int DEAD_NOT_USE_SKILL = 10011;
    // 技能cd中
    int SKILL_CD = 10012;
    // 蓝量不足
    int MP_NOT_ENOUGH = 10013;
    // 穿戴装备失败
    int EQUIP_ERROR = 10014;
    // 没有穿戴的道具
    int NOT_EQUIP = 10015;
    // 背包已满
    int PACK_FULL = 10016;
    // 技能达到等级上限
    int SKILL_LEVEL_MAX = 10017;
    // 技能学习条件不足
    int SKILL_CONDITION_NOT = 10018;
    // 已经学习了技能，不能重复学习了
    int SKILL_IS_LEARN = 10019;
    // 技能栏没有该技能，无法学习
    int NOT_SKILL_IN_SLOT = 10020;
    // 移动失败
    int MOVE_ERROR = 10021;
    // 技能栏没有技能，无法使用
    int NOT_USE_SKILL_NOT_SKILL = 10022;
    // 无法对该目标使用技能
    int NOT_USE_SKILL_TO_TARGET = 10023;
    //没有学习该技能 无法使用
    int NOT_LEARN_SKILL = 10024;
    // 技能达到等级上限
    int SKILL_LEVEL_LIMINT = 10025;
    // 技能升级条件不足
    int SKILL_UPGRADE_INSUFFICIENT = 10026;
    // 没有学习该技能,无法升级
    int NOT_UPGRADE_NOT_LEARN = 10027;
    // 背包中道具数量不足
    int PACK_ITEM_NUM_INSUFFICIENT = 10028;
    // 你已经死了无法移动
    int DEAD_NOT_MOVE = 10029;
    // 离开地图失败
    int LEAVE_MAP_ERROR = 10030;
    //没有目标地图
    int NOT_TARGET_MAP = 10031;
    /**
     * 没有开启目标地图
     */
    int NOT_OPEN_MAP = 10032;
    /**
     * 不能前往该地图
     */
    int NOT_ENTER_MAP = 10033;
    /**
     * 任务没有完成，无法领奖
     */
    int RECEIVE_REWARD_ERROR = 10034;
    /**
     * 不能接受该任务
     */
    int NOT_ACCEPT_TASK = 10035;
    /**
     * 没有该任务
     */
    int NOT_TASK = 10036;
    /**
     * 你已经加入工会了，无法创建工会
     */
    int NOT_CREATE_UNION = 10037;
    /**
     * 你已经加入行会了，无法申请加入其他行会
     */
    int NOT_APPLY_UNION = 10038;
    /**
     * 工会不存在
     */
    int UNION_NOT_EXIST = 10039;
    /**
     * 工会申请达到最大限制
     */
    int UNION_APPLY_MAX_LIMIT = 10040;
    /**
     * 你不在工会中
     */
    int NOT_JOIN_UNION = 10041;
    /**
     * 目标已经加入其他工会
     */
    int JOIN_OTHER_UNION = 10042;
    /**
     * 你没有权限
     */
    int NOT_PERMISSION = 10043;
    /**
     * 工会人数已满
     */
    int UNION_NUM_LIMIT = 10044;
    /**
     * 玩家不在申请列表中
     */
    int NOT_APPLY = 10045;
    /**
     * 不能退出工会，请先移交会长位置
     */
    int NOT_EXIT_UNION = 10046;
    /**
     * 无法操作
     */
    int NOT_HANDLE = 10047;
    /**
     * 目标不在工会中
     */
    int TARGET_NOT_JOIN_UNION = 10048;
    /**
     * 权限不合法
     */
    int PERMISSION_ERROR = 10049;
    /**
     * 自己不能修改自己的权限
     */
    int NOT_UPDATE_MYSELF = 10050;
    /**
     * 必须会长才能解散行会
     */
    int NOT_DISBAND = 10051;

}

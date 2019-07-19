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

}

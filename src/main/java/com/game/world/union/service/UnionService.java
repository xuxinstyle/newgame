package com.game.world.union.service;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 9:08
 */
public interface UnionService {
    /**
     * 创建工会
     *
     * @param player
     * @param unionName
     */
    void createUnion(String player, String unionName);

    /**
     * 同意申请操作
     *
     * @param handleAccountId 操作者
     * @param unionId         工会id
     * @param accountId       目标账号
     */
    void doAgreeApply(String handleAccountId, String unionId, String accountId);

    /**
     * 同意申请操作
     *  @param accountId       操作者
     * @param targetAccountId 目标账号id
     */
    void agreeApply(String accountId, String targetAccountId);

    /**
     * 退出工会
     *
     * @param accountId
     */
    void exitUnion(String accountId);

    /**
     * 申请加入行会
     *
     * @param accountId
     * @param unionId
     */
    void applyJoinUnion(String accountId, String unionId);

    /**
     * 查看申请列表
     *
     * @param accountId
     */
    void showApplyList(String accountId);

    /**
     * 拒绝加入工会申请
     *
     * @param handleAccounrId 操作者
     * @param targetAccountId 申请者
     */
    void refuse(String handleAccounrId, String targetAccountId);

    /**
     * 查看工会成员信息
     *
     * @param accountId
     * @param unionId
     */
    void showUnionMembers(String accountId, String unionId);

    /**
     * 查看工会列表
     *
     * @param accountId
     */
    void showUnionList(String accountId);

    /**
     * 踢人操作
     *
     * @param accountId
     * @param targetAccountId
     */
    void kickOther(String accountId, String targetAccountId);


    /**
     * 修改玩家权限
     *
     * @param accountId
     * @param targetAccountId
     * @param permission
     */
    void updatePermission(String accountId, String targetAccountId, int permission);

    /**
     * 查看自己的工会信息
     *
     * @param accountId
     */
    void showMySelfUnion(String accountId);

    /**
     * 委任队长
     *
     * @param accountId
     * @param targetAccountId
     */
    void appoinCaptain(String accountId, String targetAccountId);

    /**
     * 解散队伍
     * @param accountId
     */
    void disband(String accountId);

    void doKickOther(String targetAccountId, String unionId, String handleAccountId);

}

package com.game.world.union.model;

import com.game.world.union.constant.UnionJob;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 8:59
 */
public class UnionMemberInfo {
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 工会职务
     */
    private UnionJob unionJob;
    /**
     * 入会时间
     */
    private long enterTime;

    public UnionJob getUnionJob() {
        return unionJob;
    }

    public void setUnionJob(UnionJob unionJob) {
        this.unionJob = unionJob;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}

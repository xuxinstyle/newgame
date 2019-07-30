package com.game.world.union.packet.bean;

/**
 * 工会成员信息
 *
 * @Author：xuxin
 * @Date: 2019/7/29 16:42
 */
public class UnionMemberVO {
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 权限
     */
    private int permission;
    /**
     * 入会时间
     */
    private long enterTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }
}

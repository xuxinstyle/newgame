package com.game.user.account.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 16:25
 * @id
 */

public class SM_CreatePlayer {
    /**
     * 角色Id
     */
    private long playerId;
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 创建角色是否成功 1：成功  2：失败
     */
    private int status;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

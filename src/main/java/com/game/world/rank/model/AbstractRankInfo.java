package com.game.world.rank.model;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 16:27
 */
public abstract class AbstractRankInfo {

    private long playerId;

    private String accountId;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

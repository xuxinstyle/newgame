package com.game.world.rank.packet.bean;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 17:53
 */
public class PlayerBattleScoreRankVO {
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 战力
     */
    private long battleScore;
    /**
     * 排名
     */
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

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

    public long getBattleScore() {
        return battleScore;
    }

    public void setBattleScore(long battleScore) {
        this.battleScore = battleScore;
    }
}

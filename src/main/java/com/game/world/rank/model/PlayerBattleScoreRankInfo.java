package com.game.world.rank.model;

/**
 * 玩家战力排行榜中的 信息
 *
 * @Author：xuxin
 * @Date: 2019/7/31 16:16
 */
public class PlayerBattleScoreRankInfo extends AbstractRankInfo implements Comparable<PlayerBattleScoreRankInfo> {


    private long battleScore;

    private int level;


    public long getBattleScore() {
        return battleScore;
    }

    public void setBattleScore(long battleScore) {
        this.battleScore = battleScore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(PlayerBattleScoreRankInfo o) {
        if (this.battleScore > o.getBattleScore()) {
            return -1;
        } else if (this.battleScore == o.getBattleScore()) {
            if (this.getPlayerId() < o.getPlayerId()) {
                return -1;
            } else if (this.getPlayerId() == o.getPlayerId()) {
                return 0;
            }
        }
        return 1;
    }
}

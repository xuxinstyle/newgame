package com.game.world.rank.service;

import com.game.world.rank.model.BattleScoreRank;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 16:20
 */
@Component
public class RankManager {

    private BattleScoreRank battleScoreRank;

    public void init() {
        BattleScoreRank battleScoreRank = new BattleScoreRank();
        battleScoreRank.init();
        this.battleScoreRank = battleScoreRank;
    }

    public BattleScoreRank getBattleScoreRank() {
        return battleScoreRank;
    }

    public void setBattleScoreRank(BattleScoreRank battleScoreRank) {
        this.battleScoreRank = battleScoreRank;
    }
}

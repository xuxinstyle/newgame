package com.game.world.rank.packet;

import com.game.world.rank.packet.bean.PlayerBattleScoreRankVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 17:52
 */
public class SM_ShowRankList {

    private PlayerBattleScoreRankVO myselfRank;

    private List<PlayerBattleScoreRankVO> rankVOList;

    public List<PlayerBattleScoreRankVO> getRankVOList() {
        return rankVOList;
    }

    public void setRankVOList(List<PlayerBattleScoreRankVO> rankVOList) {
        this.rankVOList = rankVOList;
    }

    public PlayerBattleScoreRankVO getMyselfRank() {
        return myselfRank;
    }

    public void setMyselfRank(PlayerBattleScoreRankVO myselfRank) {
        this.myselfRank = myselfRank;
    }
}

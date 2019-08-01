package com.game.world.rank.service;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.util.SendPacketUtil;
import com.game.world.rank.model.BattleScoreRank;
import com.game.world.rank.model.PlayerBattleScoreRankInfo;
import com.game.world.rank.packet.SM_ShowRankList;
import com.game.world.rank.packet.bean.PlayerBattleScoreRankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 11:10
 */
@Component
public class RankServiceImpl implements RankService {

    @Autowired
    private RankManager rankManager;

    @Override
    public void init() {
        rankManager.init();
    }

    @Override
    public void doBattleScoreChange(Player player) {
        BattleScoreRank battleScoreRank = rankManager.getBattleScoreRank();
        battleScoreRank.updateRandByPlayer(player);
    }

    @Override
    public void showRankList(String accountId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        SM_ShowRankList sm = new SM_ShowRankList();
        List<PlayerBattleScoreRankVO> rankVOList = new ArrayList<>();
        BattleScoreRank battleScoreRank = rankManager.getBattleScoreRank();
        int num = 1;
        boolean haveMe = false;
        for (PlayerBattleScoreRankInfo rankInfo : battleScoreRank.getRank().keySet()) {
            if (player.getObjectId()==rankInfo.getPlayerId()) {
                PlayerBattleScoreRankVO playerBattleScoreRankVO = PlayerBattleScoreRankVO.valueOf(rankInfo);
                playerBattleScoreRankVO.setRank(num);
                sm.setMyselfRank(playerBattleScoreRankVO);
                haveMe = true;
            }
            if (num <= battleScoreRank.getNum()) {
                PlayerBattleScoreRankVO playerBattleScoreRankVO = PlayerBattleScoreRankVO.valueOf(rankInfo);
                playerBattleScoreRankVO.setRank(num);
                rankVOList.add(playerBattleScoreRankVO);
            }
            if (haveMe && num > battleScoreRank.getNum()) {
                break;
            }
            num++;
        }
        sm.setRankVOList(rankVOList);
        SendPacketUtil.send(accountId, sm);
    }
}

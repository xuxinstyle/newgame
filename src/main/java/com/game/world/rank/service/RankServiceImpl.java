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
    public void addPlayerRankInfo(Player player) {
        BattleScoreRank battleScoreRank = rankManager.getBattleScoreRank();
        PlayerBattleScoreRankInfo playerBattleScoreRankInfo = new PlayerBattleScoreRankInfo();
        playerBattleScoreRankInfo.setBattleScore(player.getBattleScore());
        playerBattleScoreRankInfo.setLevel(player.getLevel());
        playerBattleScoreRankInfo.setAccountId(player.getAccountId());
        playerBattleScoreRankInfo.setPlayerId(player.getObjectId());
        battleScoreRank.putNewRankInfo(playerBattleScoreRankInfo);
    }

    @Override
    public void showRankList(String accountId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        SM_ShowRankList sm = new SM_ShowRankList();
        List<PlayerBattleScoreRankVO> rankVOList = new ArrayList<>();
        BattleScoreRank battleScoreRank = rankManager.getBattleScoreRank();
        Map<Long, PlayerBattleScoreRankInfo> cacheRankInfo = battleScoreRank.getCacheRankInfo();
        PlayerBattleScoreRankInfo playerBattleScoreRankInfo = cacheRankInfo.get(player.getObjectId());
        int num = 1;
        boolean haveMe = false;
        for (PlayerBattleScoreRankInfo rankInfo : battleScoreRank.getRank().keySet()) {

            PlayerBattleScoreRankVO playerBattleScoreRankVO = new PlayerBattleScoreRankVO();
            playerBattleScoreRankVO.setAccountId(rankInfo.getAccountId());
            playerBattleScoreRankVO.setBattleScore(rankInfo.getBattleScore());
            playerBattleScoreRankVO.setPlayerId(rankInfo.getPlayerId());
            playerBattleScoreRankVO.setRank(num);
            if (playerBattleScoreRankInfo.equals(rankInfo)) {
                sm.setMyselfRank(playerBattleScoreRankVO);
                haveMe = true;
            }
            if (num <= battleScoreRank.capacity) {
                rankVOList.add(playerBattleScoreRankVO);
            }
            if (haveMe && num > battleScoreRank.capacity) {
                break;
            }
            num++;

        }
        sm.setRankVOList(rankVOList);
        SendPacketUtil.send(accountId, sm);
    }
}

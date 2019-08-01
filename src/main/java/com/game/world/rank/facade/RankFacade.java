package com.game.world.rank.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.role.player.event.CreatePlayerEvent;
import com.game.world.rank.event.BattleScoreChangeEvent;
import com.game.world.rank.packet.CM_ShowRankList;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 17:30
 */
@Component
public class RankFacade {

    @HandlerAnno
    public void showRankList(TSession session, CM_ShowRankList cm) {
        SpringContext.getRankListService().showRankList(session.getAccountId());
    }

    @EventAnn
    public void battleScoreChange(BattleScoreChangeEvent event) {
        SpringContext.getRankListService().doBattleScoreChange(event.getPlayer());
    }

    @EventAnn
    public void addPlayer(CreatePlayerEvent event) {
        SpringContext.getRankListService().addPlayerRankInfo(event.getPlayer());
    }
}

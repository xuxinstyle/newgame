package com.game.world.rank.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.world.rank.event.BattleScoreChangeAsynEvent;
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
    public void battleScoreChange(BattleScoreChangeAsynEvent event) {
        SpringContext.getRankListService().doBattleScoreChange(event.getPlayer());
    }

}

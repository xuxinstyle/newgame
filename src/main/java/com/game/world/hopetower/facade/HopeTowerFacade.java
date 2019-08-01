package com.game.world.hopetower.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.role.player.event.LogoutEvent;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import com.game.world.hopetower.event.MonsterDeadEvent;
import com.game.world.hopetower.event.PlayerDeadEvent;
import com.game.world.hopetower.packet.CM_ExitHopeTower;
import com.game.world.hopetower.packet.CM_ShowHopeTowerInfo;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 17:04
 */
@Component
public class HopeTowerFacade {
    private static final Logger logger = LoggerFactory.getLogger(HopeTowerFacade.class);

    @HandlerAnno
    public void showHopeTower(TSession session, CM_ShowHopeTowerInfo cm) {
        try {
            SpringContext.getHopeTowerService().showHopeTowerInfo(session.getAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]查看希望之塔出错,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]查看希望之塔出错,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }

    }

    @EventAnn
    public void monsterDead(MonsterDeadEvent event) {
        SpringContext.getHopeTowerService().doMonsterDead(event.getMapId(), event.getSceneId(), event.getAccountId());
    }

    @EventAnn
    public void doPlayerDead(PlayerDeadEvent event) {
        SpringContext.getHopeTowerService().doPlayerDeadEvent(event.getPlayerUnit());
    }

    @HandlerAnno
    public void doExitHopeTower(TSession session, CM_ExitHopeTower cm) {
        SpringContext.getHopeTowerService().exit(session.getAccountId());
    }
}

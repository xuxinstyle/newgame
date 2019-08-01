package com.game.scence.npc.facade;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.user.task.packet.CM_TalkWithNpc;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/8/1 23:59
 */
@Component
public class NpcFacade {

    private static final Logger logger = LoggerFactory.getLogger(NpcFacade.class);

    @HandlerAnno
    public void talkWithNpc(TSession session, CM_TalkWithNpc cm) {
        try {
            SpringContext.getNpcService().talkWithNpc(session.getAccountId(), cm.getMapId(), cm.getNpcId());
        } catch (RequestException e) {
            logger.error("与[{}]npc聊天失败失败,原因[{}]", cm.getNpcId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("与[{}]npc聊天失败失败", cm.getNpcId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }
}

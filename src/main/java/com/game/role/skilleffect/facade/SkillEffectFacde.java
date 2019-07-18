package com.game.role.skilleffect.facade;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.role.skilleffect.packet.CM_ShowUnitEffect;
import com.game.util.SendPacketUtil;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 16:08
 */
@Component
public class SkillEffectFacde {
    private static final Logger logger = LoggerFactory.getLogger(SkillEffectFacde.class);

    @HandlerAnno
    public void showEffect(TSession session, CM_ShowUnitEffect cm) {
        try {
            SpringContext.getSkillEffectService().showEffect(session.getAccountId(), cm.getMapId(), cm.getTargetType(), cm.getTargetId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("查看[{}],[{}]buff效果失败", cm.getTargetType().getObjectName(), cm.getTargetId(), e);

        }
    }
}

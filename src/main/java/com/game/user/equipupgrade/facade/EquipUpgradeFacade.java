package com.game.user.equipupgrade.facade;

import com.game.SpringContext;
import com.game.user.equipupgrade.packet.CM_EquipUpgrade;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 14:45
 */
@Component
public class EquipUpgradeFacade {
    private static final Logger logger = LoggerFactory.getLogger(EquipUpgradeFacade.class);
    @HandlerAnno
    public void equipUpgrade(TSession session, CM_EquipUpgrade cm){
        try {
            SpringContext.getEquipUpgradeService().equipUpgrade(session, session.getAccountId(), cm.getEquipObjectId());
        }catch (Exception e){
            logger.error("玩家{}装备{}升级失败",session.getAccountId(),cm.getEquipObjectId());
            e.printStackTrace();
        }
    }
}

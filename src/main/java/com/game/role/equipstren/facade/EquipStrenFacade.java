package com.game.role.equipstren.facade;

import com.game.SpringContext;
import com.game.role.equipstren.packet.CM_StrenEquip;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 9:07
 */
@Component
public class EquipStrenFacade {
    private static final Logger logger = LoggerFactory.getLogger(EquipStrenFacade.class);

    @HandlerAnno
    public void stenEquip(TSession session, CM_StrenEquip cm){
        try {
            SpringContext.getEquipStrenService().strenEquip(session, cm.getItemObjectId(),cm.getAccountId());
        }catch (Exception e){
            logger.error("玩家{}强化装备{}失败！",cm.getAccountId(),cm.getItemObjectId());
            e.printStackTrace();
        }

    }
}

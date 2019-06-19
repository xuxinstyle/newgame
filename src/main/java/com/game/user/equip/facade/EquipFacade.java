package com.game.user.equip.facade;

import com.game.SpringContext;
import com.game.user.equip.packet.CM_Equip;
import com.game.user.equip.packet.CM_ShowEquipInfo;
import com.game.user.equip.packet.CM_UnEquip;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/16 19:08
 */
@Component
public class EquipFacade {
    private static Logger logger = LoggerFactory.getLogger(EquipFacade.class);

    @HandlerAnno
    public void equip(TSession session, CM_Equip cm) {
        try {
            SpringContext.getEquipService().equip(session, cm.getAccountId(), cm.getItemObjectId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("玩家{}穿戴装备{}失败", cm.getAccountId(), cm.getItemObjectId());
        }
    }

    @HandlerAnno
    public void unEquip(TSession session, CM_UnEquip cm) {
        try {
            SpringContext.getEquipService().unEquip(session, session.getAccountId(), cm.getPosition());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("玩家{}卸下装备{}失败", session.getAccountId(), cm.getPosition());
        }
    }

    @HandlerAnno
    public void showEquip(TSession session, CM_ShowEquipInfo cm) {
        try {
            SpringContext.getEquipService().showEquip(session, cm.getAccountId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查看玩家{}装备栏信息失败", session.getAccountId());
        }
    }
}

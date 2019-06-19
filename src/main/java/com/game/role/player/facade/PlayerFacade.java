package com.game.role.player.facade;

import com.event.anno.ReceiveAnn;
import com.game.role.player.packet.CM_ShowAttribute;
import com.game.user.equip.event.EquipEvent;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 20:52
 */
@Component
public class PlayerFacade {
    @ReceiveAnn
    public void doEquip(EquipEvent event){

    }

    @HandlerAnno
    public void showAttribute(TSession session, CM_ShowAttribute cm){

    }
}

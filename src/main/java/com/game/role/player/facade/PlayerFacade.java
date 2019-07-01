package com.game.role.player.facade;

import com.game.SpringContext;
import com.game.role.player.packet.CM_ShowAttribute;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 20:52
 */
@Component
public class PlayerFacade {
    private static final Logger logger = LoggerFactory.getLogger(PlayerFacade.class);
    @HandlerAnno
    public void showAttribute(TSession session, CM_ShowAttribute cm){
        try {
            SpringContext.getPlayerSerivce().showPlayerAttribute(session, cm.getAccountId());
        }catch (Exception e){
            logger.error("查看玩家{}属性失败",cm.getAccountId());
            e.printStackTrace();
        }
    }



}

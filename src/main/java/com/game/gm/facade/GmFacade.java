package com.game.gm.facade;

import com.game.SpringContext;
import com.game.gm.packet.CM_GMCommond;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/28 17:45
 */
@Component
public class GmFacade {
    @HandlerAnno
    public void doCommand(TSession session, CM_GMCommond commond){
        SpringContext.getGmService().doGmCommand(session,commond.getCommand());
    }

}

package com.game.connect.facade;

import com.game.connect.packet.CM_Connect;
import com.game.connect.packet.SM_Connect;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/5/18 15:53
 */
@Component
public class ConnectFacade {
    Logger logger = Logger.getLogger(ConnectFacade.class);

    @HandlerAnno
    public void connect(TSession session, CM_Connect req){

        SM_Connect cm = new SM_Connect();
        session.sendPacket(cm);
    }
}

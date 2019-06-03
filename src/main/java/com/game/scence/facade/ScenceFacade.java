package com.game.scence.facade;

import com.game.scence.packet.CM_EnterInitScence;
import com.game.SpringContext;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:28
 */
@Component
public class ScenceFacade {
    private static final Logger logger = LoggerFactory.getLogger(ScenceFacade.class);
    @HandlerAnno
    public void enterNoviceVillage(TSession session, CM_EnterInitScence req){
        try {

            SpringContext.getScenceSerivce().enterMap(session,req.getAccountId());
        }catch (Exception e){
            logger.error("进入地图失败",e.toString());
            e.printStackTrace();
        }
    }
}

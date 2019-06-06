package com.game.scence.facade;

import com.game.scence.packet.CM_EnterInitScence;
import com.game.SpringContext;
import com.game.scence.packet.CM_EnterMap;
import com.game.scence.packet.CM_ShowAllAccountInfo;
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
            SpringContext.getScenceSerivce().enterInitMap(session,req.getAccountId());
        }catch (Exception e){
            logger.error("进入地图失败",e.toString());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void enterMap(TSession session, CM_EnterMap req){
        try {
            SpringContext.getScenceSerivce().enterMap(session, req.getMapId());
        }catch (Exception e){
            logger.error("进入地图失败",e.toString());
            e.printStackTrace();
        }

    }
    @HandlerAnno
    public void showAllAccount(TSession session, CM_ShowAllAccountInfo req){
        try {
            SpringContext.getScenceSerivce().showAllAccountInfo(session, req.getMapId());
        }catch (Exception e){
            logger.error("请求查看场景中的账号信息失败",e.toString());
            e.printStackTrace();
        }
    }
}

package com.game.scence.facade;

import com.event.anno.ReceiveAnn;
import com.game.role.player.event.PlayerUpLevelEvent;
import com.game.scence.packet.*;
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
    public void enterMap(TSession session, CM_EnterMap req){
        try {
            SpringContext.getScenceSerivce().enterMap(session,req.getAccountId(),req.getMapId());
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
    @HandlerAnno
    public void showAllAccount(TSession session, CM_ShowAccountInfo req){
        try {
            SpringContext.getScenceSerivce().showAccountInfo(session,req.getAccountId(),req.getMapId() );
        }catch (Exception e){
            logger.error("请求查看场景中的账号信息失败",e.toString());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void move(TSession session, CM_Move cm){
        try{
            SpringContext.getScenceSerivce().move(session, cm.getX(), cm.getY(),cm.getMapId());
        }catch (Exception e){
            logger.error("请求移动失败",e.toString());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void operate(TSession session, CM_OnlinePlayerOperate cm){
        try{
            SpringContext.getScenceSerivce().showMap(cm.getMapId());
        }catch (Exception e){
            logger.error("请求移动失败",e.toString());
            e.printStackTrace();
        }
    }
    @ReceiveAnn
    public void playerUpLevel(PlayerUpLevelEvent event){
        SpringContext.getScenceSerivce().doPlayerUpLevel(event.getMapId(), event.getPlayer());
    }
}

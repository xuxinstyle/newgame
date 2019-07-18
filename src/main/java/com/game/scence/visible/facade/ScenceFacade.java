package com.game.scence.visible.facade;

import com.event.anno.EventAnn;
import com.game.common.exception.RequestException;
import com.game.role.player.event.LogoutEvent;
import com.game.role.player.event.PlayerUpLevelEvent;
import com.game.scence.visible.packet.*;
import com.game.SpringContext;
import com.game.util.SendPacketUtil;
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
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("进入地图失败",e.toString());
            e.printStackTrace();
        }
    }

    @HandlerAnno
    public void showAllAccount(TSession session, CM_ShowAllVisibleInfo req){
        try {
            SpringContext.getScenceSerivce().showAllVisibleInfo(session, req.getMapId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("请求查看场景中的账号信息失败",e.toString());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void showAccountIdInfo(TSession session, CM_ShowAccountInfo req){
        try {
            SpringContext.getScenceSerivce().showAccountIdInfo(session,req.getMapId(),req.getObjectId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("请求查看场景中的账号信息失败",e.toString());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void move(TSession session, CM_Move cm){
        try{
            SpringContext.getScenceSerivce().move(session.getAccountId(),cm.getTargetPos(),cm.getMapId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("请求移动失败",e.toString());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void showMap(TSession session, CM_ScenePositionVisible cm){
        try{
            SpringContext.getScenceSerivce().showMap(cm.getMapId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("显示地图失败",e.toString());
            e.printStackTrace();
        }
    }
    @EventAnn
    public void playerUpLevel(PlayerUpLevelEvent event){
        SpringContext.getScenceSerivce().doPlayerUpLevelSyncScene(event.getPlayer());
    }

    @HandlerAnno
    public void changeMap(TSession session, CM_ChangeMap req){
        try{

            SpringContext.getScenceSerivce().changeMap(session.getAccountId(),req.getTargetMapId(),true);
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("切换地图{}失败",req.getTargetMapId(),e);

        }
    }

    @EventAnn
    public void logout(LogoutEvent event){
        SpringContext.getScenceSerivce().doLogoutBefore(event.getAccountId());
    }
}

package com.game.scence.service;

import com.game.role.account.model.AccountInfo;
import com.game.role.account.packet.SM_EnterCreatePlayer;
import com.game.SpringContext;
import com.game.role.account.entity.AccountEnt;
import com.game.scence.constant.SceneType;
import com.game.scence.packet.SM_EnterInitScence;
import com.game.scence.packet.SM_Move;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:42
 */
@Component
public class ScenceServiceImpl implements ScenceService {
    private static final Logger logger = LoggerFactory.getLogger(ScenceServiceImpl.class);

    @Autowired
    private ScenceManger scenceManger;

    @Override
    public void enterInitMap(TSession session, String accountId) {
        /**
         * 如果没有角色信息，则进入创角页面
         */
        if(!checkAccountInfo(accountId)){
            SM_EnterCreatePlayer sm = new SM_EnterCreatePlayer();
            sm.setAccountId(accountId);

            session.sendPacket(sm);
            logger.info("没有角色信息");
        }else {
            /**
             * 如果有角色信息，则进入玩家上次在的地图
             */
            logger.info("有角色信息！");
            doEnterMap(session, accountId);
        }
    }

    @Override
    public void enterMap(TSession session, int mapId) {
        String accountId = session.getAccountId();
        if(accountId==null){
            logger.warn("Session中的accountId为空");
            return;
        }
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        SceneType sceneType = SceneType.valueOf(mapId);
        if(sceneType==null){
            logger.warn("没有目标地图信息");
            return;
        }
        accountInfo.setCurrentMapType(sceneType);
        SpringContext.getAccountService().save(accountEnt);
        if(logger.isDebugEnabled()){
            logger.debug("切换地图成功");
        }
        SM_Move sm = new SM_Move();
        sm.setStatus(1);
        session.sendPacket(sm);
    }

    private void doEnterMap(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(logger.isDebugEnabled()){
            logger.debug("进入地图："+accountEnt.toString(),accountInfo);
        }

        SM_EnterInitScence sm = new SM_EnterInitScence();
        sm.setAccountId(accountId);
        sm.setType(accountInfo.getLastLogoutMapType().getMapid());
        session.sendPacket(sm);
    }

    /**
     * 判断玩家是否创角色 如果玩家没有角色 则进入创角
     * @param accountId
     * @return
     */
    private boolean checkAccountInfo(String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        /**
         * 如玩家没有昵称，则说明没有创角色信息
         */
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        logger.info(accountInfo.toString());

        if(accountInfo.getAccountName()==null||accountInfo.getAccountName().equals("")){
            return false;
        }
        return true;
    }


}

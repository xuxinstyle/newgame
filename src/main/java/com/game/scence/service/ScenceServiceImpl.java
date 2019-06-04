package com.game.scence.service;

import com.game.base.account.model.AccountInfo;
import com.game.base.account.packet.SM_EnterCreatePlayer;
import com.game.scence.constant.SceneType;
import com.game.SpringContext;
import com.game.base.account.entity.AccountEnt;
import com.game.scence.packet.CM_EnterInitScence;
import com.game.scence.packet.SM_EnterInitScence;
import com.game.scence.resource.TestResource;
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
    public void enterMap(TSession session, String accountId) {
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

    private void doEnterMap(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(logger.isDebugEnabled()){
            logger.debug("进入地图："+accountEnt.toString(),accountInfo);
        }

        SM_EnterInitScence sm = new SM_EnterInitScence();
        sm.setAccountId(accountId);
        sm.setType(accountInfo.getCurrentMapType().getMapid());
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

        if(accountInfo.getNickName()==null||accountInfo.getNickName().equals("")){
            return false;
        }
        return true;
    }


}

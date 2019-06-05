package com.game.login.service;

import com.game.SpringContext;
import com.game.base.account.entity.AccountEnt;
import com.game.base.account.model.AccountInfo;
import com.game.login.packet.SM_Login;
import com.game.login.packet.SM_LoginNoAcount;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/1 15:58
 */
@Component
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public void login(TSession session, String username, String passward) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(username);
        if(accountEnt==null){
            SM_LoginNoAcount sm = new SM_LoginNoAcount();
            session.sendPacket(sm);
            return ;
        }
        if (accountEnt.getPassward()==null||accountEnt.getPassward().isEmpty()||!accountEnt.getPassward().equals(passward)) {
            SM_Login sm = new SM_Login();
            sm.setStatus(0);
            sm.setAccountId(username);
            session.sendPacket(sm);
            logger.info(accountEnt.getAccountId() + "密码错误！");
            return;
        }
        /** 踢对方下线*/
        if(SessionManager.getSessionByAccount(username)!=null){
            SessionManager.logout(username);
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        SM_Login sm = new SM_Login();
        sm.setStatus(1);
        sm.setAccountId(accountEnt.getAccountId());
        sm.setLastScenceId(accountInfo.getLastLogoutMapType().getMapid());
        session.sendPacket(sm);
        session.setAccountId(username);
        SessionManager.addAccountSessionMap(username, session);
        //进入场景地图 如果玩家没有昵称就显示取昵称的界面
        logger.info(accountEnt.getAccountId() + "登录成功！");


    }
}

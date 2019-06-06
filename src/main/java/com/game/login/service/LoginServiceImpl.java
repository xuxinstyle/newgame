package com.game.login.service;

import com.game.SpringContext;
import com.game.login.packet.SM_Logout;
import com.game.role.account.entity.AccountEnt;
import com.game.role.account.model.AccountInfo;
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
            logout(session, username);
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(accountInfo.getAccountName()==null||accountInfo.getLastLogoutMapType()==null){

            SM_Login sm = new SM_Login();
            sm.setStatus(1);
            sm.setAccountId(accountEnt.getAccountId());
            session.sendPacket(sm);
            session.setAccountId(username);
            SessionManager.addAccountSessionMap(username, session);
            accountInfo.setLastLoginTime(System.nanoTime());
            SpringContext.getAccountService().save(accountEnt);
            //进入场景地图 如果玩家没有昵称就显示取昵称的界面
            logger.info(accountEnt.getAccountId() + "登录成功！");
        }else{
            accountInfo.setLastLoginTime(System.nanoTime());
            SM_Login sm = new SM_Login();
            sm.setStatus(1);
            sm.setAccountId(accountEnt.getAccountId());
            sm.setLastScenceId(accountInfo.getLastLogoutMapType().getMapid());
            session.sendPacket(sm);
            session.setAccountId(username);
            SessionManager.addAccountSessionMap(username, session);
            SpringContext.getAccountService().save(accountEnt);
            //进入场景地图 如果玩家没有昵称就显示取昵称的界面
            logger.info(accountEnt.getAccountId() + "登录成功！");
        }
    }

    @Override
    public void logout(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        accountInfo.setLastLogoutMapType(accountInfo.getCurrentMapType());
        accountInfo.setLastLogoutTime(System.nanoTime());
        SpringContext.getAccountService().save(accountEnt);

        SM_Logout sm = new SM_Logout();
        session.sendPacket(sm);
        session.logout(accountId);
        SpringContext.getSessionManager().removeSession(accountId);
        SpringContext.getScenceSerivce().removeScenceAccountId(accountInfo.getCurrentMapType().getMapid(), accountId);
    }
}

package com.game.login.service;

import com.game.SpringContext;
import com.game.login.packet.SM_Logout;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.login.packet.SM_Login;
import com.game.login.packet.SM_LoginNoAcount;
import com.game.scence.constant.SceneType;
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
    public void login(TSession session, String username, String passward) throws InterruptedException {
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
        /** TODO: */
        TSession tSession = SessionManager.getSessionByAccount(username);
        if(tSession!=null){
            SM_Logout sm = new SM_Logout();
            tSession.sendPacket(sm);
            while(SessionManager.getSessionByAccount(username)!=null){
                Thread.sleep(500);
            }
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        accountInfo.setLastLoginTime(System.nanoTime());
        SM_Login sm = new SM_Login();
        sm.setStatus(1);
        sm.setAccountId(accountEnt.getAccountId());
        if(accountInfo.getLastLogoutMapType()==null){
            accountInfo.setLastLogoutMapType(SceneType.NoviceVillage);
        }
        sm.setLastScenceId(accountInfo.getLastLogoutMapType().getMapid());
        session.sendPacket(sm);
        /** 将accountId放到session中,并将session放到缓存中管理*/
        SessionManager.addAccountSessionMap(username, session);

        SpringContext.getAccountService().save(accountEnt);
        logger.info(accountEnt.getAccountId() + "登录成功！");
    }

    @Override
    public void logout(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        if(accountEnt==null){
            logger.error("没有账号信息");
            return;
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(accountInfo.getCurrentMapType()==null){
            accountInfo.setLastLogoutMapType(SceneType.NoviceVillage);
        }else {
            accountInfo.setLastLogoutMapType(accountInfo.getCurrentMapType());
            accountInfo.setLastLogoutTime(System.nanoTime());
            SpringContext.getAccountService().save(accountEnt);
            session.logout(accountId);
            SpringContext.getSessionManager().removeSession(accountId);
            SpringContext.getScenceSerivce().removeScenceAccountId(accountInfo.getCurrentMapType().getMapid(), accountId);

        }
    }
}

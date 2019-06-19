package com.game.login.service;

import com.game.SpringContext;
import com.game.login.packet.SM_Logout;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.login.packet.SM_Login;
import com.game.login.packet.SM_LoginNoAcount;
import com.game.scence.constant.SceneType;
import com.game.util.MD5Util;
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

        int serverId = SpringContext.getServerConfigValue().getServerId();
        String passwardDB = MD5Util.inputPassToDbPass(passward, MD5Util.saltDB);

        String usernameDB = username+"_"+serverId;
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(usernameDB);
        if(accountEnt==null){
            SM_LoginNoAcount sm = new SM_LoginNoAcount();
            session.sendPacket(sm);
            return ;
        }
        if (accountEnt.getPassward()==null||accountEnt.getPassward().isEmpty()||!accountEnt.getPassward().equals(passwardDB)) {
            SM_Login sm = new SM_Login();
            sm.setStatus(0);
            sm.setAccountId(usernameDB);
            session.sendPacket(sm);
            return;
        }
        /** 踢对方下线*/

        /** 本项目设计时考虑多线程所以在分发时将登录操作单独放在一个线程中执行 有可能一个号刚登录进来还没已经清理了上个登录的登录信息， 后面又来一个登录的 发现没有登录信息，所以直接就进入到游戏中*/
        TSession tSession = SessionManager.getSessionByAccount(usernameDB);
        if(tSession!=null){
            SM_Logout sm = new SM_Logout();
            tSession.sendPacket(sm);
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        accountInfo.setLastLoginTime(System.nanoTime());
        SM_Login sm = new SM_Login();

        if(accountInfo.getLastLogoutMapType()==null){
            accountInfo.setLastLogoutMapType(SceneType.NoviceVillage);
        }
        sm.setLastScenceId(accountInfo.getLastLogoutMapType().getMapId());
        /** 将accountId放到session中,并将session放到缓存中管理*/
        SessionManager.addAccountSessionMap(usernameDB, session);
        SpringContext.getAccountService().save(accountEnt);

        sm.setStatus(1);
        sm.setAccountId(accountEnt.getAccountId());
        session.sendPacket(sm);
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
            SpringContext.getSessionManager().removeSession(accountId);
            SpringContext.getScenceSerivce().removeScenceAccountId(accountInfo.getCurrentMapType().getMapId(), accountId);
            accountInfo.setCurrentMapType(null);
            SpringContext.getAccountService().save(accountEnt);
            session.logout(accountId);
        }
    }
}

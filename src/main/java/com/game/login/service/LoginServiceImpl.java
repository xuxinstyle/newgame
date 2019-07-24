package com.game.login.service;

import com.game.SpringContext;
import com.game.login.event.LoginEvent;
import com.game.login.packet.SM_Logout;
import com.game.role.player.event.LogoutEvent;
import com.game.role.player.model.Player;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.login.packet.SM_Login;
import com.game.login.packet.SM_LoginNoAcount;
import com.game.scence.visible.constant.MapType;
import com.game.util.MD5Util;
import com.game.util.TimeUtil;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

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
        String passwardDB = MD5Util.inputPassToDbPass(passward, MD5Util.SALTDB);

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
        TSession tSession = SpringContext.getSessionService().getSession(usernameDB);
        if(tSession!=null){
            SM_Logout sm = new SM_Logout();
            tSession.sendPacket(sm);
            SM_Login res = new SM_Login();
            res.setStatus(-1);
            session.sendPacket(res);
            return;
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        accountInfo.setLastLoginTime(TimeUtil.now());
        Player player = SpringContext.getPlayerSerivce().getPlayer(usernameDB);
        SM_Login sm = new SM_Login();
        int targetMapId = MapType.NoviceVillage.getId();
        if (player != null) {
            targetMapId = player.getLastLogoutMapId();
        }
        /** 将accountId放到session中,并将session放到缓存中管理*/
        SessionManager.addAccountSessionMap(usernameDB, session);
        SpringContext.getAccountService().save(accountEnt);
        /**
         * 抛出玩家登陆事件
         */
        LoginEvent event = LoginEvent.valueOf(usernameDB);
        SpringContext.getEvenManager().syncSubmit(event);
        Set<String> onlineAccounts = SpringContext.getSessionManager().getOnlineAccounts();
        onlineAccounts.add(usernameDB);
        sm.setStatus(1);
        sm.setAccountId(usernameDB);
        if(player!=null) {
            sm.setPlayerId(player.getObjectId());
        }
        session.sendPacket(sm);
        SpringContext.getScenceSerivce().loginAfterEnterMap(session, session.getAccountId(), targetMapId);
        logger.info(usernameDB + "登录成功！");
    }

    @Override
    public void logout(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        /**
         * 登出时处理场景相关信息
         */
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        if(player.getCurrMapId()==0){
            player.setLastLogoutMapId(MapType.NoviceVillage.getId());
        }
        player.setLastLogoutMapId(player.getCurrMapId());
        accountInfo.setLastLogoutTime(TimeUtil.now());
        /**
         * 登出时抛出登出事件
         */
        SpringContext.getSessionManager().removeSession(accountId);

        LogoutEvent event = LogoutEvent.valueOf(accountId);
        SpringContext.getEvenManager().syncSubmit(event);
        SpringContext.getAccountService().save(accountEnt);
        Set<String> onlineAccounts = SpringContext.getSessionManager().getOnlineAccounts();
        onlineAccounts.remove(accountId);
        session.logout(accountId);
    }
}

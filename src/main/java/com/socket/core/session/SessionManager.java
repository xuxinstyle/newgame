package com.socket.core.session;

import com.game.login.packet.SM_Logout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 20:55
 */
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    // <玩家账号Id, Session>
    private static Map<String, TSession> accountSessionMap = new ConcurrentHashMap<>();

    public static TSession getSessionByAccount(String accountId){
        if(accountSessionMap.get(accountId)==null){
            logger.info("该玩家没有已下线");
            return null;
        }
        return accountSessionMap.get(accountId);
    }
    public static void addAccountSessionMap(String accountId, TSession session){
        accountSessionMap.put(accountId, session);
    }

    public static void logout(String accountId){
        TSession sessionByAccount = getSessionByAccount(accountId);
        SM_Logout sm = new SM_Logout();
        sessionByAccount.sendPacket(sm);
        sessionByAccount.logout();
        accountSessionMap.remove(accountId);

    }

}

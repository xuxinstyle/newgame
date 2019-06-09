package com.socket.core.session;

import com.game.SpringContext;
import com.game.login.packet.SM_Logout;
import com.game.role.account.entity.AccountEnt;
import com.game.role.account.model.AccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 20:55
 */
@Component
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    // <玩家账号Id, Session>
    private static Map<String, TSession> accountSessionMap = new ConcurrentHashMap<>();

    public static TSession getSessionByAccount(String accountId) {
        if (logger.isDebugEnabled()) {
            logger.debug("");
        }
        if (accountSessionMap.get(accountId) == null) {
            return null;
        }
        return accountSessionMap.get(accountId);
    }

    public static void addAccountSessionMap(String accountId, TSession session) {
        session.setAccountId(accountId);
        accountSessionMap.putIfAbsent(accountId, session);
    }


    public void removeSession(String accountId) {
        accountSessionMap.remove(accountId);
    }

    public static void clearSession() {
        for (Map.Entry<String, TSession> entry : accountSessionMap.entrySet()) {
            TSession session = entry.getValue();
            if (!session.isActive()) {
                accountSessionMap.remove(entry.getKey());
            }
        }
    }
    public Map<String, TSession> getAccountSessionMap(){
        return accountSessionMap;
    }

}

package com.socket.core.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 20:55
 */
@Component
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    /** <玩家账号Id, Session> */
    /**
     * FIXME:对这个map操作要考虑多线程安全问题： ConcurrentHashMap就已经保证了线程安全
     */
    private static Map<String, TSession> accountSessionMap = new ConcurrentHashMap<>();

    /**
     * 所以在线的玩家账号id
     */
    private Set<String> onlineAccounts = new CopyOnWriteArraySet();

    public TSession getSessionByAccount(String accountId) {
        if (logger.isDebugEnabled()) {
            logger.debug("");
        }
        if (accountId == null) {
            return null;
        }
        return accountSessionMap.get(accountId);
    }

    public static void addAccountSessionMap(String accountId, TSession session) {
        session.setAccountId(accountId);
        accountSessionMap.put(accountId, session);
    }


    public void removeSession(String accountId) {
        accountSessionMap.remove(accountId);
    }

    public Map<String, TSession> getAccountSessionMap(){
        return accountSessionMap;
    }

    public static void setAccountSessionMap(Map<String, TSession> accountSessionMap) {
        SessionManager.accountSessionMap = accountSessionMap;
    }

    public Set<String> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(Set<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }
}

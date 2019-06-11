package com.game.base.executor.common;

import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import com.socket.dispatcher.core.ActionDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 22:07
 */
@Component
public class CommonExecutor {

    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
    // 每五分钟清理一次Session
    public static void start(){
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SessionManager.clearSession();
            }
        }, 1,5,TimeUnit.MINUTES);
    }

    public static void addTask(TSession session, int opIndex, Object pack){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                ActionDispatcher.doHandle(session,opIndex,pack);
            }
        });
    }
}

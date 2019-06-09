package com.executor;

import com.socket.Utils.NameThreadFactory;
import com.socket.core.session.SessionManager;
import com.game.util.TimeUtil;
import java.util.concurrent.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 22:07
 */
public class CommonExecutor {
    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
    // 每五分钟清理一次Session
    public static void start(){
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SessionManager.clearSession();
            }
        }, 1,5,TimeUnit.MINUTES);
    }

}

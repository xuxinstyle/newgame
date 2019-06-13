package com.game.base.executor.common.command;

import com.game.base.executor.common.Impl.AbstractCommonRateCommand;
import com.socket.core.session.SessionManager;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 12:24
 */
public class ClearSessionCommand extends AbstractCommonRateCommand {

    public ClearSessionCommand(long period, long delay) {
        super(period, delay);
    }

    @Override
    public String getName() {
        return "clear Session";
    }

    @Override
    public void active() {
        SessionManager.clearSession();
    }
}

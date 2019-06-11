package com.game.base.executor.account;

import com.game.base.executor.account.Impl.AbstractAccountCommond;
import com.socket.core.session.TSession;
import com.socket.dispatcher.core.ActionDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:57
 */
public class MessageCommond extends AbstractAccountCommond {

    private TSession session;
    private int opIndex;
    private Object packet;

    public MessageCommond(TSession session, int opIndex, Object packet,String accountId) {
        super(accountId);
        this.session = session;
        this.opIndex = opIndex;
        this.packet = packet;
    }


    @Override
    public void active() {
        ActionDispatcher.doHandle(session, opIndex, packet);
    }
}

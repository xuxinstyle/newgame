package com.game.base.executor.account.command;

import com.game.base.executor.account.impl.AbstractAccountCommand;
import com.socket.core.session.TSession;
import com.socket.dispatcher.core.ActionDispatcher;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:57
 */
public class MessageCommand extends AbstractAccountCommand {

    private TSession session;
    private int opIndex;
    private Object packet;

    public MessageCommand(TSession session, int opIndex, Object packet, String accountId) {
        super(accountId);
        this.session = session;
        this.opIndex = opIndex;
        this.packet = packet;
    }


    @Override
    public String getName() {
        return "Message Commond";
    }

    @Override
    public void active() {
        ActionDispatcher.doHandle(session, opIndex, packet);
    }


}

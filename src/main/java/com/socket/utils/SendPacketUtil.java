package com.socket.utils;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:56
 */
public class SendPacketUtil {
    public static final void send(Player player, Object object){
        SpringContext.getSessionService().sendPacket(player.getAccountId(),object);
    }
    public static void send(TSession session, Object object){
        SpringContext.getSessionService().sendPacket(session,object);
    }
    public static void send(String accountId, Object object){
        SpringContext.getSessionService().sendPacket(accountId, object);
    }
}

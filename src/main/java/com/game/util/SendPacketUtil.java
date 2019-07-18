package com.game.util;

import com.game.SpringContext;
import com.game.common.packet.SM_Exception;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:56
 */
public class SendPacketUtil {
    public static void send(Player player, Object object){
        SpringContext.getSessionService().sendPacket(player.getAccountId(),object);
    }
    public static void send(TSession session, Object object){
        SpringContext.getSessionService().sendPacket(session,object);
    }
    public static void send(String accountId, Object object){
        SpringContext.getSessionService().sendPacket(accountId, object);
    }
    public static void send(long playerId, Object object){
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        send(player.getAccountId(),object);
    }

    public static void send(String accountId, int i18nId) {
        send(accountId, SM_Exception.valueOf(i18nId));
    }
}

package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeIdEnum;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.event.PlayerUpLevelEvent;
import com.game.role.player.model.Player;
import com.game.role.player.packet.SM_PlayerUpLevel;
import com.game.role.player.resource.PlayerLevelResource;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;

import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 14:19
 */
public class ExpUseEffect extends AbstractUseEffect {
    /**
     * 增加的经验
     */
    private long addExp;

    @Override
    public void init(String effect,Map<String,Object> param) {
        String replace = effect.replace(".0", "");
        this.addExp = Long.parseLong(replace);
    }

    @Override
    public void use(String accountId,int num) {
        TSession session = SessionManager.getSessionByAccount(accountId);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        PlayerLevelResource playerLevelResource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel());
        if (playerLevelResource == null) {
            return;
        }
        long upLevelExp = playerLevelResource.getUpLevelExp();
        player.setExp(player.getExp() + addExp*num);
        SpringContext.getPlayerSerivce().save(playerEnt);
        while (player.getExp() >= upLevelExp) {
            playerLevelResource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel()+1);
            if (playerLevelResource == null) {
                SM_PlayerUpLevel res = new SM_PlayerUpLevel();
                res.setPlayerName(player.getPlayerName());
                res.setStatus(2);
                session.sendPacket(res);
                return;
            }
            player.setLevel(player.getLevel() + 1);
            player.setExp(player.getExp() - upLevelExp);

            PlayerLevelResource lastplayerLevelResource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel() - 1);

            if(playerLevelResource==null){
                return;
            }
            List<Attribute> baseAttributeListEnd = lastplayerLevelResource.getBaseAttributeList();
            player.getAttributeContainer().putAndCcomputeAttributes(AttributeIdEnum.LEVEL,baseAttributeListEnd,true);
            SpringContext.getPlayerSerivce().save(playerEnt);

            PlayerUpLevelEvent playerUpLevelEvent = PlayerUpLevelEvent.valueOf( player);
            SpringContext.getEvenManager().syncSubmit(playerUpLevelEvent);

            upLevelExp = playerLevelResource.getUpLevelExp();
            SM_PlayerUpLevel sm = new SM_PlayerUpLevel();
            sm.setPlayerName(player.getPlayerName());
            sm.setLevel(player.getLevel() - 1);
            sm.setUpLevel(player.getLevel());
            sm.setStatus(1);
            session.sendPacket(sm);
        }

    }

    public long getAddExp() {
        return addExp;
    }

    public void setAddExp(long addExp) {
        this.addExp = addExp;
    }
}

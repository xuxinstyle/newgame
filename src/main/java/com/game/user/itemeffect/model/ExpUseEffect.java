package com.game.user.itemeffect.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeIdEnum;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.event.PlayerUpLevelEvent;
import com.game.role.player.model.Player;
import com.game.role.player.packet.SM_PlayerUpLevel;
import com.game.role.player.resource.PlayerLevelResource;
import com.game.scence.fight.command.AddAttributeBuffSynCommand;
import com.game.util.PlayerUtil;
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
    public void active(String accountId, int num) {
        TSession session = SpringContext.getSessionService().getSession(accountId);
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
            if (player.getLevel()>=PlayerUtil.PLAYER_MAX_LEVEL||playerLevelResource == null) {
                SM_PlayerUpLevel res = new SM_PlayerUpLevel();
                res.setPlayerName(player.getPlayerName());
                res.setStatus(2);
                session.sendPacket(res);
                return;
            }

            player.setLevel(player.getLevel() + 1);
            player.setExp(player.getExp() - upLevelExp);

            List<Attribute> baseAttributeListEnd = playerLevelResource.getBaseAttributeList();
            player.getAttributeContainer().putAndComputeAttributes(AttributeIdEnum.BASE,baseAttributeListEnd);
            SpringContext.getPlayerSerivce().save(playerEnt);
            // 抛同步事件 当玩家在战斗的时候升级，玩家的血和蓝回满
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

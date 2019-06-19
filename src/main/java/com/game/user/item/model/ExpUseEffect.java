package com.game.user.item.model;

import com.game.SpringContext;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.event.PlayerUpLevelEvent;
import com.game.role.player.model.Player;
import com.game.role.player.packet.SM_PlayerUpLevel;
import com.game.role.player.resource.PlayerLevelResource;
import com.game.scence.constant.SceneType;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 14:19
 */
public class ExpUseEffect extends UseEffect {
    /**
     * 增加的经验
     */
    private long addExp;

    @Override
    public void init(String effect) {
        this.addExp = Long.parseLong(effect);
    }

    @Override
    public void use(String accountId) {
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        PlayerLevelResource playerLevelResource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel());
        if (playerLevelResource == null) {
            return;
        }
        long upLevelExp = playerLevelResource.getUpLevelExp();
        player.setExp(player.getExp() + addExp);
        SpringContext.getPlayerSerivce().save(playerEnt);
        while (player.getExp() >= upLevelExp) {
            player.setLevel(player.getLevel() + 1);
            player.setExp(player.getExp() - upLevelExp);
            SpringContext.getPlayerSerivce().save(playerEnt);

            TSession session = SessionManager.getSessionByAccount(accountId);
            SM_PlayerUpLevel sm = new SM_PlayerUpLevel();
            sm.setPlayerName(player.getPlayerName());
            sm.setLevel(player.getLevel() - 1);
            sm.setUpLevel(player.getLevel());
            session.sendPacket(sm);
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
            AccountInfo accountInfo = accountEnt.getAccountInfo();
            SceneType currentMapType = accountInfo.getCurrentMapType();
            PlayerUpLevelEvent playerUpLevelEvent = PlayerUpLevelEvent.valueOf(currentMapType.getMapid(), player);
            SpringContext.getEvenManager().syncSubmit(playerUpLevelEvent);
            playerLevelResource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel());
            if (playerLevelResource == null) {
                return;
            }
            upLevelExp = playerLevelResource.getUpLevelExp();
        }

    }

    public long getAddExp() {
        return addExp;
    }

    public void setAddExp(long addExp) {
        this.addExp = addExp;
    }
}

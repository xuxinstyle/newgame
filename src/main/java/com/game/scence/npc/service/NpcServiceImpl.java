package com.game.scence.npc.service;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.role.player.model.Player;
import com.game.scence.npc.resource.NpcResource;
import com.game.user.task.event.TalkEvent;
import com.game.util.I18nId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 11:21
 */
@Component
public class NpcServiceImpl implements NpcService {
    @Autowired
    private NpcManager npcManager;

    @Override
    public List<NpcResource> getAllMapNpcResource(int mapId) {
        return npcManager.getAllMapNpcResource(mapId);
    }

    @Override
    public List<Integer> getNpcIds(int mapId) {
        return npcManager.getNpcIds(mapId);
    }
    @Override
    public NpcResource getNpcResource(int id){
        return npcManager.getNpcResource(id);
    }

    @Override
    public void talkWithNpc(String accountId, int mapId, int npcId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        NpcResource npcResource = SpringContext.getNpcService().getNpcResource(npcId);
        if (npcResource == null || npcResource.getMapId() != mapId) {
            // 没有npc
            RequestException.throwException(I18nId.NOT_NPC);
        }

        SpringContext.getEvenManager().syncSubmit(TalkEvent.valueOf(player, npcId));
    }
}

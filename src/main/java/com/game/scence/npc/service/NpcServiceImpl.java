package com.game.scence.npc.service;

import com.game.scence.npc.resource.NpcResource;
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
}

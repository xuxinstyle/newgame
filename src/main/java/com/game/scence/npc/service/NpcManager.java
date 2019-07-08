package com.game.scence.npc.service;

import com.game.scence.npc.resource.NpcResource;
import com.game.scence.visible.resource.MapResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 11:21
 */
@Component
public class NpcManager {
    @Autowired
    private StorageManager storageManager;

    public List<NpcResource> getAllMapNpcResource(int mapId){
        Collection<NpcResource> resourceAll = (Collection<NpcResource>)storageManager.getResourceAll(NpcResource.class);
        List<NpcResource> npcResourceList = new ArrayList<>();
        for(NpcResource resource:resourceAll){
            if(resource==null){
                continue;
            }
            if(resource.getMapId()==mapId){
                npcResourceList.add(resource);
            }
        }
        return npcResourceList;
    }

    public List<Integer> getNpcIds(int mapId) {
        Collection<NpcResource> resourceAll = (Collection<NpcResource>)storageManager.getResourceAll(NpcResource.class);
        List<Integer> npcIdList = new ArrayList<>();
        for(NpcResource resource:resourceAll){
            if(resource==null){
                continue;
            }
            if(resource.getMapId()==mapId){
                npcIdList.add(resource.getId());
            }
        }
        return npcIdList;
    }

    public NpcResource getNpcResource(int id){
        return storageManager.getResource(id,NpcResource.class);
    }
}

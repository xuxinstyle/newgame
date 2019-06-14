package com.game.scence.service;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.scence.constant.SceneType;
import com.game.scence.model.ScenceInfo;
import com.game.scence.resource.MapResource;
import com.resource.core.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 15:10
 */
@Component
public class ScenceManger {

    private static final Logger logger = LoggerFactory.getLogger(ScenceManger.class);
    /**
     * 放场景中的玩家信息
     */
    private static Map<Integer, ScenceInfo> scenceAccountIdMap = new ConcurrentHashMap<>(SceneType.values().length);

    public Object getResource(String id, Class<?> clz){
        return StorageManager.getResource(clz, id);
    }
    public Collection<?> getResourceAll(Class<?> clz){
        return StorageManager.getResourceAll(clz);
    }
    public ScenceInfo getScenceInfo(int mapId){
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        return scenceInfo;
    }
    public void init(){
        Collection<MapResource> resourceAll = (Collection<MapResource>) getResourceAll(MapResource.class);
        for(MapResource resource:resourceAll){
            int id = resource.getId();
            scenceAccountIdMap.put(id,ScenceInfo.valueOf(id,null));
        }
    }
    /**
     * 存在并发问题
     * @param mapId
     * @param accountId
     */
    public void setScenceInfo(int mapId, String accountId){
        if(scenceAccountIdMap.get(mapId)==null){
            scenceAccountIdMap.put(mapId,ScenceInfo.valueOf(mapId,accountId));
            return;
        }
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        List<Player> player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        scenceInfo.setPlayers(player);

    }

    public void removeAccountId(int mapId, String accountId){
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        if(scenceInfo==null){
            return;
        }
        List<Player> players = SpringContext.getPlayerSerivce().getPlayer(accountId);
        for(Player player:players) {
            scenceInfo.remove(player);
        }
    }

    public void refreshScenceInfo(int mapId, Player player) {
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        scenceInfo.refresh(player);
    }
}

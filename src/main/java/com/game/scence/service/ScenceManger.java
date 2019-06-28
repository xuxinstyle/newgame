package com.game.scence.service;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.scence.constant.SceneType;
import com.game.scence.model.ScenceInfo;
import com.game.scence.resource.MapResource;
import com.resource.core.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 15:10
 */
@Component
public class ScenceManger {
    @Autowired
    private StorageManager storageManager;

    private static final Logger logger = LoggerFactory.getLogger(ScenceManger.class);
    /**
     * 放场景中的玩家信息
     */
    private static Map<Integer, ScenceInfo> scenceAccountIdMap = new ConcurrentHashMap<>(SceneType.values().length);

    /**
     * 获取某张表的某个id字段对应的资源
     * @param id
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T getResource(Object id, Class<T> clz){
        return storageManager.getResource(id,clz);
    }

    /**
     * 获取该表的所有资源
     * @param clz
     * @return
     */
    public Collection<?> getResourceAll(Class<?> clz){
        return storageManager.getResourceAll(clz);
    }

    public ScenceInfo getScenceInfo(int mapId){
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        return scenceInfo;
    }

    public void init(){
        Collection<MapResource> resourceAll = (Collection<MapResource>) getResourceAll(MapResource.class);
        for(MapResource resource:resourceAll){
            scenceAccountIdMap.put(resource.getId(),ScenceInfo.valueOf(resource));
        }

    }
    /**
     * 存在并发问题
     * @param mapId
     * @param
     */
    public void setScenceInfo(int mapId, Player player){
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        scenceInfo.setPlayerList(player);

    }

    public void removeAccountId(int mapId, String accountId){
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        if(scenceInfo==null){
            return;
        }
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);

        scenceInfo.remove(player);

    }

    public void refreshScenceInfo(int mapId, Player player) {
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        scenceInfo.refresh(player);
    }
}

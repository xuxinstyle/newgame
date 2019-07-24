package com.game.scence.visible.service;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;
import com.resource.core.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
     * <MapId, 地图中的数据>
     *     todo:存在副本中分线的概念的话map中的key可以换成用字符串拼接的方式
     *     todo：或者，每个场景的id都是唯一的，即sceneid唯一 如果分线的话 <mapid,MapInfo>
     *         其中mapInfo可存放分线场景
     *     todo：这里到第四阶段要大改，先暂时这样做
     */
    private Map<Integer, AbstractScene> scenceMap = new HashMap<>(MapType.values().length);
    /**
     * 副本场景对象信息《accountId,AbstractScene》
     */
    private Map<String, AbstractScene> copySceneMap = new HashMap<>();

    /**
     * 存放unit
     */
    private Map<Long, PlayerUnit> playerUnitMap = new HashMap<>();

    public void init(){
        Collection<MapResource> resourceAll = (Collection<MapResource>) storageManager.getResourceAll(MapResource.class);
        for(MapResource resource:resourceAll){
            if (resource.isNeedToInit()) {
                MapType mapType = MapType.getMapType(resource.getId());
                AbstractScene scene = mapType.create();
                scene.init();
                scenceMap.put(mapType.getId(), scene);
            }
        }

    }

    public PlayerUnit getPlayerUnit(Player player) {
        PlayerUnit playerUnit = playerUnitMap.get(player.getObjectId());
        if (playerUnit == null) {
            playerUnit = PlayerUnit.valueOf(player);
            playerUnitMap.put(player.getObjectId(), playerUnit);
        }
        return playerUnit;
    }

    public void putPlayerUnit(Player player) {
        playerUnitMap.put(player.getObjectId(), PlayerUnit.valueOf(player));
    }

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

    public void putCopyScene(String accountId, AbstractScene scene) {
        copySceneMap.put(accountId, scene);
    }

    public void removeCopyScene(String accountId) {
        copySceneMap.remove(accountId);
    }

    /**
     * 获取地图的场景信息实体和
     *
     * @param mapId
     * @param accountId
     * @return
     */
    public AbstractScene getScence(int mapId, String accountId) {
        AbstractScene scenceInfo = scenceMap.get(mapId);
        if (scenceInfo == null) {
            // 如果玩家已经创建了副本？玩家不可能已经创建了副本 除非他正在副本中
            AbstractScene abstractScene = copySceneMap.get(accountId);
            return abstractScene;
        }
        return scenceInfo;
    }

    public Map<Integer, AbstractScene> getScenceMap() {
        return scenceMap;
    }

    public void setScenceMap(Map<Integer, AbstractScene> scenceMap) {
        this.scenceMap = scenceMap;
    }

    public Map<String, AbstractScene> getCopySceneMap() {
        return copySceneMap;
    }

    public void setCopySceneMap(Map<String, AbstractScene> copySceneMap) {
        this.copySceneMap = copySceneMap;
    }
}

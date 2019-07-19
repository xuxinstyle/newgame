package com.game.scence.visible.service;

import com.game.scence.base.model.AbstractScene;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;
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
     * <MapId, 地图中的数据>
     *     todo:存在副本中分线的概念的话map中的key可以换成用字符串拼接的方式
     *     todo：或者，每个场景的id都是唯一的，即sceneid唯一 如果分线的话 <mapid,MapInfo>
     *         其中mapInfo可存放分线场景
     *     todo：这里到第四阶段要大改，先暂时这样做
     */
    private static Map<Integer, AbstractScene> scenceMap = new ConcurrentHashMap<>(MapType.values().length);

    public void init(){
        Collection<MapResource> resourceAll = (Collection<MapResource>) storageManager.getResourceAll(MapResource.class);
        for(MapResource resource:resourceAll){
            MapType mapType = MapType.getMapType(resource.getId());
            AbstractScene scene = mapType.create();
            scene.init();
            scenceMap.put(mapType.getMapId(), scene);
        }

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

    public AbstractScene getScence(int mapId){
        AbstractScene scenceInfo = scenceMap.get(mapId);
        return scenceInfo;
    }

    public static Map<Integer, AbstractScene> getScenceMap() {
        return scenceMap;
    }

    public static void setScenceMap(Map<Integer, AbstractScene> scenceMap) {
        ScenceManger.scenceMap = scenceMap;
    }
}

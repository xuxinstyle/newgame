package com.game.scence.service;

import com.game.scence.constant.SceneType;
import com.game.scence.resource.TestResource;
import com.resource.Storage;
import com.resource.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private static Map<Integer, List<String>> scenceAccountIdMap = new ConcurrentHashMap<>(SceneType.values().length);

    public Object getResource(String id, Class<?> clz){
        return StorageManager.getResource(clz, id);
    }

    public List<String> getScenceAccountIds(int mapId){
        List<String> accountIds = scenceAccountIdMap.get(mapId);
        return accountIds;
    }

    /**
     * 这里问问要加同步吗
     * @param mapId
     * @param accountId
     */
    public void setScenceAccountId(int mapId, String accountId){

        if (scenceAccountIdMap.get(mapId) == null) {
            List<String> accountIds = new ArrayList<>();
            accountIds.add(accountId);
            scenceAccountIdMap.put(mapId, accountIds);
        }else {
            List<String> strings = scenceAccountIdMap.get(mapId);
            if(strings.contains(accountId)){
                return;
            }
            strings.add(accountId);
        }

    }

    public void removeAccountId(int mapId, String accountId){
        List<String> strings = scenceAccountIdMap.get(mapId);
        if(strings==null){
            logger.warn("没有["+mapId+"]场景的信息，移除玩家信息失败");
            return;
        }
        if(!strings.contains(accountId)){
            logger.warn("在["+mapId+"]场景中没有["+accountId+"]的信息，移除信息失败");
            return;
        }
        strings.remove(accountId);
    }
}

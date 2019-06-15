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
    /*uncheck*/
    public void init(){
        Collection<MapResource> resourceAll = (Collection<MapResource>) getResourceAll(MapResource.class);
        for(MapResource resource:resourceAll){
            int id = resource.getId();
            MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(id);
            String context = mapResource.getContext();
            String[] mapY = context.split(",");
            if(mapY.length<0) {
                logger.info("地图{}资源加载错误",resource);
                return;
            }
            String[] mapX = mapY[0].split(" ");
            int[][] mapcontext = new int[mapX.length][mapY.length];
            for (int i = 0; i < mapY.length; i++) {
                String[] mapj = mapY[i].split(" ");
                for(int j = 0; j < mapj.length ;j++){
                    mapcontext[i][j] = Integer.parseInt(mapj[j]);
                }
            }
            for(int i=0;i<mapcontext.length;i++){

                for(int j=0;j<mapcontext[0].length;j++){
                    System.out.print(mapcontext[i][j]+" ");
                }
                System.out.println();
            }
            scenceAccountIdMap.put(id,ScenceInfo.valueOf(id,mapcontext));
        }

    }
    /**
     * 存在并发问题
     * @param mapId
     * @param List<Player>
     */
    public void setScenceInfo(int mapId, List<Player> players){
        ScenceInfo scenceInfo = scenceAccountIdMap.get(mapId);
        scenceInfo.setPlayers(players);

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

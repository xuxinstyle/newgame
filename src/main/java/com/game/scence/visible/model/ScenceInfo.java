package com.game.scence.visible.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.scence.visible.resource.MapResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:43
 */
public class ScenceInfo {
    private int mapId;
    /** 角色*/
    private List<Player> playerList;
    /**地图坐标信息信息*/
    private MapResource mapResource;
    /**
     * 地图中npc信息 npc配置id
     */
    private List<Integer> npcInfoList;

    public static ScenceInfo valueOf(MapResource mapResource) {
        ScenceInfo scenceInfo = new ScenceInfo();
        List<Player> playerList = new ArrayList<>();
        scenceInfo.playerList = playerList;
        scenceInfo.setMapId(mapResource.getId());
        scenceInfo.setMapResource(mapResource);
        List<Integer> npcIds = SpringContext.getNpcService().getNpcIds(mapResource.getId());

        scenceInfo.setNpcInfoList(npcIds);
        return scenceInfo;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Integer> getNpcInfoList() {
        return npcInfoList;
    }

    public void setNpcInfoList(List<Integer> npcInfoList) {
        this.npcInfoList = npcInfoList;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public MapResource getMapResource() {
        return mapResource;
    }

    public void setMapResource(MapResource mapResource) {
        this.mapResource = mapResource;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Player player) {
        this.playerList.add(player);
    }
    public void remove(Player rplayer){
        for (int i = 0; i< playerList.size(); i++){
            if(playerList.get(i).getObjectId()==rplayer.getObjectId()){
                playerList.remove(i);
            }
        }

    }

    public void refresh(Player player) {
        for (int i = 0; i< playerList.size(); i++){
            if(playerList.get(i).getObjectId()==player.getObjectId()){
                playerList.set(i,player);
            }
        }
    }
}

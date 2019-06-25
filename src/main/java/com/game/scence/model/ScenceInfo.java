package com.game.scence.model;

import com.game.role.player.model.Player;
import com.game.scence.resource.MapResource;

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

    public static ScenceInfo valueOf(int mapId, MapResource mapResource) {
        ScenceInfo scenceInfo = new ScenceInfo();
        List<Player> playerList = new ArrayList<>();
        scenceInfo.playerList = playerList;
        scenceInfo.setMapId(mapId);
        scenceInfo.setMapResource(mapResource);
        return scenceInfo;
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
                playerList.get(i).setX(player.getX());
                playerList.get(i).setY(player.getY());
                playerList.get(i).setLevel(player.getLevel());
            }
        }
    }
}

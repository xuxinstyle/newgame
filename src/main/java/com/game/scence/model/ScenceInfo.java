package com.game.scence.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.scence.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:43
 */
public class ScenceInfo {
    private int mapId;
    /** 角色*/
    private List<Player> players;
    /**地图坐标信息信息*/
    private MapResource mapResource;

    public static ScenceInfo valueOf(int mapId, MapResource mapResource) {
        ScenceInfo scenceInfo = new ScenceInfo();
        List<Player> players = new ArrayList<>();
        scenceInfo.players = players;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Player player) {
        this.players.add(player);
    }
    public void remove(Player rplayer){
        for (int i = 0;i<players.size();i++){
            if(players.get(i).getObjectId()==rplayer.getObjectId()){
                players.remove(i);
            }
        }

    }

    public void refresh(Player player) {
        for (int i = 0;i<players.size();i++){
            if(players.get(i).getObjectId()==player.getObjectId()){
                players.get(i).setX(player.getX());
                players.get(i).setY(player.getY());
                players.get(i).setLevel(player.getLevel());
            }
        }
    }
}

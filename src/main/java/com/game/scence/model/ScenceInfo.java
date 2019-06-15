package com.game.scence.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:43
 */
public class ScenceInfo {

    /** 地图id*/
    private int mapId;
    /** 角色*/
    private List<Player> players;
    /**地图坐标信息信息*/
    private int[][] mapContext;

    public static ScenceInfo valueOf(int mapId, int[][] mapContext ) {
        ScenceInfo scenceInfo = new ScenceInfo();
        scenceInfo.setMapId(mapId);
        List<Player> players = new ArrayList<>();
        scenceInfo.players = players;

        return scenceInfo;
    }

    public int[][] getMapContext() {
        return mapContext;
    }

    public void setMapContext(int[][] mapContext) {
        this.mapContext = mapContext;
    }

    public boolean checkPlayer(Player player){
        for(Player player1 :players){
            if(player1.getObjectId()==player.getObjectId()){
                return true;
            }
        }
        return false;
    }
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        if(this.players ==null){
            this.players = new ArrayList<>();
        }
        this.players.addAll(players);
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
            }
        }
    }
}

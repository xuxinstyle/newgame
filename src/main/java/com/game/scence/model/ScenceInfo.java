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
    private static final Logger logger = LoggerFactory.getLogger(ScenceInfo.class);
    /** 地图id*/
    private int mapId;
    /** 角色*/
    private List<Player> players;

    public static ScenceInfo valueOf(int mapId, String accountId) {
        ScenceInfo scenceInfo = new ScenceInfo();
        scenceInfo.setMapId(mapId);
        List<Player> player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        if(player==null){
            logger.warn("账号[{}]没有创建角色",accountId);
            return null;
        }
        scenceInfo.setPlayers(player);
        return scenceInfo;
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

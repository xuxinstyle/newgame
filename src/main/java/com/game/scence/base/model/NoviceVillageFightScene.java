package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.npc.resource.NpcResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 16:10
 */
public class NoviceVillageFightScene extends AbstractScene {
    /**
     * 地图中npc信息 npc配置id
     */
    private List<Integer> npcInfoList;
    /**
     * 场景中玩家的角色信息
     */
    private List<Player> players;
    public NoviceVillageFightScene(){
        super();
    }
    /**
     * todo：注意以后处理分线
     */
    @Override
    public void init() {
        setMapId(MapType.NoviceVillage.getMapId());
        setPlayers(new ArrayList<>());
        setSceneId(0);
        setAccountIds(new ArrayList<>());
        this.npcInfoList = SpringContext.getNpcService().getNpcIds(getMapId());
    }

    public NoviceVillageFightScene(int mapId) {
        super(mapId);
    }
    @Override
    public Map<Integer, List<Position>> getVisiblePosition() {
        /**
         * npc位置
         */
        Map<Integer, List<Position>> positionMap= new HashMap<>();
        List<Position> npcPositions = new ArrayList<>();

        for(Integer resourceId:npcInfoList){
            NpcResource npcResource = SpringContext.getNpcService().getNpcResource(resourceId);
            npcPositions.add(Position.valueOf(npcResource.getPx(),npcResource.getPy()));
        }
        positionMap.put(ObjectType.NPC.getTypeId(),npcPositions);
        /**
         * 玩家位置
         */
        List<Position> playerPositions = new ArrayList<>();
        for(Player player:players){
            playerPositions.add(player.getPosition());
        }
        positionMap.put(ObjectType.PLAYER.getTypeId(),playerPositions);
        return positionMap;
    }

    @Override
    public void move(String accountId, Position targetpos) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        long playerId = accountInfo.getPlayerId();

        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        player.setPosition(targetpos);
        SpringContext.getPlayerSerivce().save(playerEnt);
    }

    @Override
    public void leave(String accountId) {
        super.leave(accountId);
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        players.remove(player);
    }

    @Override
    public void enter(Player player) {
        super.enter(player);
        if(players==null){
            players = new ArrayList<>();
        }
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public MapType getMapType() {
        return MapType.NoviceVillage;
    }


    public List<Integer> getNpcInfoList() {
        return npcInfoList;
    }

    public void setNpcInfoList(List<Integer> npcInfoList) {
        this.npcInfoList = npcInfoList;
    }

}

package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.FightAccount;
import com.game.scence.npc.resource.NpcResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
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
public class NoviceVillageScene extends AbstractScene {
    /**
     * 地图中npc信息 npc配置id
     */
    private List<Integer> npcInfoList;

    public NoviceVillageScene(){
        super();
    }
    /**
     * todo：注意以后处理分线
     */
    @Override
    public void init() {
        setMapId(MapType.NoviceVillage.getMapId());
        setSceneId(0);
        setFightAccounts(new HashMap<>());
        this.npcInfoList = SpringContext.getNpcService().getNpcIds(getMapId());
    }

    public NoviceVillageScene(int mapId) {
        super(mapId);
    }

    @Override
    public List<VisibleVO> getVisibleVOList() {
        /**
         * npc信息 TODO:npc信息暂时不做操作
         */
        /**
         * 玩家信息
         */
        return super.getVisibleVOList();
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
    }

    @Override
    public List<String> getAccountIds() {
        return super.getAccountIds();
    }

    @Override
    public void enter(Player player) {
        player.setCurrMapId(MapType.NoviceVillage.getMapId());
        super.enter(player);

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

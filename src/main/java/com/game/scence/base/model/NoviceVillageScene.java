package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;

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

    @Override
    public void init() {
        setMapId(MapType.NoviceVillage.getMapId());

        this.npcInfoList = SpringContext.getNpcService().getNpcIds(getMapId());
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
         * npc位置 FIXME: 这里也不用处理npc
         */
        return super.getVisiblePosition();
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
    public CreatureUnit getUnit(ObjectType objectType, long objectId) {
        return super.getUnit(objectType, objectId);
    }

    @Override
    public void leave(Player player) {
        super.leave(player);
    }

    @Override
    public List<String> getAccountIds() {
        return super.getAccountIds();
    }

    @Override
    public void enter(Player player) {
        player.setCurrMapId(MapType.NoviceVillage.getMapId());
        SpringContext.getPlayerSerivce().save(player);
        super.enter(player);
    }

    @Override
    public boolean isCanUseSkill() {
        return false;
    }

    @Override
    public int getMapId() {
        return MapType.NoviceVillage.getMapId();
    }

    public List<Integer> getNpcInfoList() {
        return npcInfoList;
    }

    public void setNpcInfoList(List<Integer> npcInfoList) {
        this.npcInfoList = npcInfoList;
    }

}

package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;

import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.scence.visible.resource.MapResource;

import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 16:10
 */
public class NoviceVillageScene extends AbstractScene {
    /**
     * fixme: 这里先放这里 未来任务有需要在地图中npc信息 npc配置id
     */
    private List<Integer> npcInfoList;

    @Override
    public void init() {
        setMapId(MapType.NoviceVillage.getId());
        setSceneId(0);
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
    public CreatureUnit getUnit(ObjectType objectType, long objectId) {
        return super.getUnit(objectType, objectId);
    }

    @Override
    public void doLeave(Player player) {
        super.doLeave(player);
    }

    @Override
    public List<String> getAccountIds() {
        return super.getAccountIds();
    }



    @Override
    public boolean isCanUseSkill() {
        return false;
    }

    @Override
    public int getMapId() {
        return MapType.NoviceVillage.getId();
    }

    @Override
    public boolean canChangeToMap(int targetMapId) {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(targetMapId);
        if (mapResource == null) {
            return false;
        }

        return true;
    }

    public List<Integer> getNpcInfoList() {
        return npcInfoList;
    }

    public void setNpcInfoList(List<Integer> npcInfoList) {
        this.npcInfoList = npcInfoList;
    }

}

package com.game.scence.base.model;


import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;

import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 21:10
 */
public abstract class AbstractScene {
    /**
     * 地图id
     */
    private int mapId;
    /**
     * 场景id
     */
    private int sceneId;

    /**
     * 玩家战斗单元信息
     */
    private Map<Long, CreatureUnit> creatureUnitMap = new HashMap<>();


    public AbstractScene(){

    }
    public AbstractScene(int mapId){
        this(mapId,0);
    }

    public AbstractScene(int mapId, int sceneId){
        this.mapId = mapId;
        this.sceneId = sceneId;
    }
    /**
     * 获取可视对象的信息
     */
    public List<VisibleVO> getVisibleVOList(){
        /**
         * 玩家的可视信息
         */
        List<VisibleVO> visibleVOList = new ArrayList<>();
        Map<Long, CreatureUnit> creatureUnitMap = getCreatureUnitMap();
        for (CreatureUnit creatureUnit : creatureUnitMap.values()) {
            if (creatureUnit.isDead()) {
                continue;
            }
            VisibleVO visibleVO = new VisibleVO();
            visibleVO.setObjectId(creatureUnit.getId());
            visibleVO.setType(creatureUnit.getType());
            visibleVO.setPosition(creatureUnit.getPosition());
            visibleVO.setVisibleName(creatureUnit.getVisibleName());
            visibleVO.setCurrHp(creatureUnit.getCurrHp());
            visibleVO.setCurrMp(creatureUnit.getCurrMp());
            visibleVO.setMaxHp(creatureUnit.getMaxHp());
            visibleVO.setMaxMp(creatureUnit.getMaxMp());
            visibleVOList.add(visibleVO);
        }
        return visibleVOList;
    }

    public CreatureUnit getUnit(ObjectType objectType, long objectId) {
        if (objectType == ObjectType.PLAYER) {
            return creatureUnitMap.get(objectId);
        }
        return null;
    }
    /**
     * FIXME:只是用来客户端显示
     * @return
     */
    public Map<Integer, List<Position>> getVisiblePosition(){
        Map<Integer, List<Position>> positionMap= new HashMap<>();
        for (CreatureUnit creatureUnit : creatureUnitMap.values()) {
            List<Position> positions = positionMap.get(creatureUnit.getType().getTypeId());
            if (positions == null) {
                positions = new ArrayList<>();
                positionMap.put(creatureUnit.getType().getTypeId(), positions);
            }
            positions.add(creatureUnit.getPosition());
        }

        return positionMap;
    }
    /**
     * 用于客户端获取场景中的玩家信息
     * @return
     */
    public List<String> getAccountIds(){
        List<String> accountIds = new ArrayList<>();
        for (CreatureUnit creatureUnit : creatureUnitMap.values()) {
            accountIds.add(creatureUnit.getAccountId());
        }
        return accountIds;
    }
    /**
     * 移动
     * @param accountId
     * @param targetpos
     */
    public abstract void move(String accountId,Position targetpos);

    /**
     * 初始化地图
     */
    public abstract void init();

    /**
     * 是否可以释放技能
     */
    public boolean isCanUseSkill(){
        return false;
    }

    public void enter(Player player){
        creatureUnitMap.put(player.getObjectId(), PlayerUnit.valueOf(player));
    }

    public void leave(Player player) {
        CreatureUnit creatureUnit = creatureUnitMap.get(player.getObjectId());
        creatureUnit.clearAllCommand();
        creatureUnitMap.remove(player.getObjectId());
    }

    public Map<Long, CreatureUnit> getCreatureUnitMap() {
        return creatureUnitMap;
    }

    public void setCreatureUnitMap(Map<Long, CreatureUnit> creatureUnitMap) {
        this.creatureUnitMap = creatureUnitMap;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }


}

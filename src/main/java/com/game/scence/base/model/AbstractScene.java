package com.game.scence.base.model;


import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;

import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.util.I18nId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 21:10
 */
public abstract class AbstractScene {

    public static final AtomicInteger SCENE_ID = new AtomicInteger(Integer.MAX_VALUE - 1);
    /**
     * 地图id
     */
    private int mapId;
    /**
     * 场景id 唯一id
     */
    private int sceneId;
    /**
     * 场景是否结束
     */
    private boolean isEnd = false;

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
     * @param playerId
     * @param targetpos
     */
    public boolean moveAndThrow(long playerId, Position targetpos) {
        CreatureUnit creatureUnit = getCreatureUnitMap().get(playerId);
        if (creatureUnit == null) {
            return false;
        }
        if (creatureUnit.isDead()) {
            RequestException.throwException(I18nId.DEAD_NOT_MOVE);
        }
        creatureUnit.setPosition(targetpos);
        return true;
    }

    /**
     * 初始化地图
     */
    public abstract void init();

    /**
     * 是否可以释放技能
     */
    public abstract boolean isCanUseSkill();

    /**
     * 进入地图的操作
     *
     * @param player
     */
    public void doEnter(Player player) {
        player.setCurrMapId(getMapId());
        player.setCurrSceneId(getSceneId());
        SpringContext.getPlayerSerivce().save(player);
        //
        PlayerUnit playerUnit = PlayerUnit.valueOf(player);
        creatureUnitMap.put(player.getObjectId(), playerUnit);
        playerUnit.setScene(this);
    }

    /**
     * 做离开操作
     *
     * @param player
     */
    public void doLeave(Player player) {
        CreatureUnit creatureUnit = creatureUnitMap.get(player.getObjectId());
        if (creatureUnit == null) {
            return;
        }
        creatureUnit.reset();
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

    /**
     * 做副本的结算
     */
    public void doEnd() {

    }

    /**
     * 判断从当前地图能否进入到目标地图
     *
     * @param targetMapId
     * @return
     */
    public abstract boolean canChangeToMap(int targetMapId);

    /**
     * 判断能否进入该地图 有些地图有特殊的进入 如副本中当玩家进入个人副本之后，其他玩家无法进入
     *
     * @param player
     * @return
     */
    public boolean canEnter(Player player) {
        return true;
    }

    /**
     * 判断能否离开地图 如玩家进入地图后 玩家无法离开
     *
     * @return
     */
    public boolean isCanLeave() {
        return true;
    }



}

package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.resource.MonsterResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.util.I18nId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 野外，玩家相互可视
 * @Author：xuxin
 * @Date: 2019/7/3 15:49
 */

public class FieldFightScene extends AbstractScene {
    /**
     * 怪物战斗单元
     */
    private Map<Long,MonsterUnit> monsterUnits = new HashMap<>();

    @Override
    public void init() {
        setMapId(MapType.FIELD.getMapId());
        setSceneId(0);
        List<MonsterResource> mapMonsterResources = SpringContext.getMonsterService().getMapMonsterResources(getMapId());
        if(mapMonsterResources==null){
            return;
        }
        for(MonsterResource resource:mapMonsterResources){
            int monsterNum = resource.getMonsterNum();
            for(int i = 0;i<monsterNum;i++) {
                MonsterUnit monsterUnit = MonsterUnit.valueOf(resource);
                monsterUnit.setId(SpringContext.getIdentifyService().getNextIdentify(ObjectType.MONSTER));
                monsterUnit.setMapId(MapType.FIELD.getMapId());
                monsterUnits.put(monsterUnit.getId(),monsterUnit);
            }
        }

    }

    @Override
    public CreatureUnit getUnit(ObjectType objectType, long objectId) {
        if (objectType == ObjectType.MONSTER) {
            return monsterUnits.get(objectId);
        }
        if (objectType == ObjectType.PLAYER) {
            return getCreatureUnitMap().get(objectId);
        }
        return null;
    }

    @Override
    public List<VisibleVO> getVisibleVOList() {
        /**
         * 玩家可是信息
         */
        List<VisibleVO> visibleVOList = super.getVisibleVOList();
        /**
         * 怪物的可视信息
         */
        for(MonsterUnit monsterUnit:monsterUnits.values()){
            if (monsterUnit.isDead()) {
                continue;
            }
            VisibleVO visibleVO = new VisibleVO();
            visibleVO.setVisibleName(monsterUnit.getVisibleName());
            visibleVO.setPosition(monsterUnit.getPosition());
            visibleVO.setType(monsterUnit.getType());
            visibleVO.setObjectId(monsterUnit.getId());
            visibleVO.setCurrHp(monsterUnit.getCurrHp());
            visibleVO.setCurrMp(monsterUnit.getCurrMp());
            visibleVO.setMaxHp(monsterUnit.getMaxHp());
            visibleVO.setMaxMp(monsterUnit.getMaxMp());
            visibleVOList.add(visibleVO);
        }
        return visibleVOList;
    }

    @Override
    public Map<Integer, List<Position>> getVisiblePosition() {
        Map<Integer, List<Position>> positionMap = super.getVisiblePosition();

        for(MonsterUnit monsterUnit:monsterUnits.values()){
            List<Position> positions = positionMap.get(monsterUnit.getType().getTypeId());
            if(positions==null){
                positions =  new ArrayList<>();
                positionMap.put(monsterUnit.getType().getTypeId(),positions);
            }
            positions.add(monsterUnit.getPosition());
        }
        return positionMap;
    }

    @Override
    public boolean isCanUseSkill() {
        return true;
    }

    @Override
    public void leave(Player player) {
        super.leave(player);
    }

    @Override
    public List<String> getAccountIds() {
        List<String> accountIds = new ArrayList<>();
        for (CreatureUnit creatureUnit : getCreatureUnitMap().values()) {
            accountIds.add(creatureUnit.getAccountId());
        }
        return accountIds;
    }

    @Override
    public void enter(Player player) {
        player.setCurrMapId(MapType.FIELD.getMapId());
        SpringContext.getPlayerSerivce().save(player);
        super.enter(player);
    }

    public MapType getMapType() {
        return MapType.FIELD;
    }

    public Map<Long, MonsterUnit> getMonsterUnits() {
        return monsterUnits;
    }

    public void setMonsterUnits(Map<Long, MonsterUnit> monsterUnits) {
        this.monsterUnits = monsterUnits;
    }
}

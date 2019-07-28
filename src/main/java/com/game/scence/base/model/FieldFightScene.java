package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 野外，玩家相互可视
 * @Author：xuxin
 * @Date: 2019/7/3 15:49
 */

public class FieldFightScene extends AbstractMonsterScene {


    @Override
    public void init() {
        setMapId(MapType.FIELD.getId());
        setSceneId(0);
        setSceneId(SCENE_ID.getAndDecrement());
        //初始化怪物
        Map<Long, MonsterUnit> monsterUnits = SpringContext.getMonsterService().getMonsterUnits(getMapId());
        for (MonsterUnit monsterUnit : monsterUnits.values()) {
            monsterUnit.setScene(this);
        }
        setMonsterUnits(monsterUnits);

    }
    @Override
    public CreatureUnit getUnit(ObjectType objectType, long objectId) {
        if (objectType == ObjectType.MONSTER) {
            return getMonsterUnits().get(objectId);
        }
        if (objectType == ObjectType.PLAYER) {
            return getCreatureUnitMap().get(objectId);
        }
        return null;
    }

    @Override
    public boolean isCanUseSkill() {
        return true;
    }

    @Override
    public boolean canChangeToMap(int targetMapId) {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(targetMapId);
        if (mapResource == null) {
            return false;
        }
        if (mapResource.getMapType() == MapType.NoviceVillage.getMapId()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canEnter(Player player) {
        if (player.getCurrMapId() == MapType.NoviceVillage.getMapId()) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAccountIds() {
        List<String> accountIds = new ArrayList<>();
        for (CreatureUnit creatureUnit : getCreatureUnitMap().values()) {
            accountIds.add(creatureUnit.getAccountId());
        }
        return accountIds;
    }


    public MapType getMapType() {
        return MapType.FIELD;
    }
}

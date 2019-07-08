package com.game.scence.field.model;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractFightScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.FightAccount;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.resource.MonsterResource;
import com.game.scence.npc.resource.NpcResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 野外，玩家相互可视
 * @Author：xuxin
 * @Date: 2019/7/3 15:49
 */

public class FieldFightScene extends AbstractFightScene {

    private Map<Long,MonsterUnit> monsterUnits = new HashMap<>();

    public FieldFightScene(){
        super();
    }

    @Override
    public void init() {
        setAccountIds(new ArrayList<>());
        setCommandMap(new HashMap<>());
        setFightAccounts(new HashMap<>());
        setMapId(MapType.FIELD.getMapId());
        setSceneId(0);
        setFightAccounts(new HashMap<>());
        List<MonsterResource> mapMonsterResources = SpringContext.getMonsterService().getMapMonsterResources(getMapId());
        if(mapMonsterResources==null){
            return;
        }
        for(MonsterResource resource:mapMonsterResources){
            int monsterNum = resource.getMonsterNum();
            for(int i = 0;i<monsterNum;i++) {
                MonsterUnit monsterUnit = MonsterUnit.valueOf(resource);
                monsterUnit.setId(SpringContext.getIdentifyService().getNextIdentify(ObjectType.MONSTER));
                monsterUnits.put(monsterUnit.getId(),monsterUnit);
            }
        }

    }


    public FieldFightScene(int mapId) {
        super(mapId);
    }

    @Override
    public List<VisibleVO> getVisibleVOList() {
        List<VisibleVO> visibleVOList = new ArrayList<>();
        /**
         * 怪物的可视信息
         */
        for(MonsterUnit monsterUnit:monsterUnits.values()){
            VisibleVO visibleVO = new VisibleVO();
            visibleVO.setVisibleName(monsterUnit.getVisibleName());
            visibleVO.setPosition(monsterUnit.getPosition());
            visibleVO.setType(monsterUnit.getType());
            visibleVO.setObjectId(monsterUnit.getId());
            visibleVOList.add(visibleVO);
        }
        /**
         * 玩家的可视信息
         */
        Map<String, FightAccount> fightAccounts = getFightAccounts();
        for(FightAccount fightAccount:fightAccounts.values()){
            Map<Long, CreatureUnit> creatureUnitMap = fightAccount.getCreatureUnitMap();
            if(creatureUnitMap==null){
                continue ;
            }
            for(CreatureUnit creatureUnit:creatureUnitMap.values()) {
                VisibleVO visibleVO = new VisibleVO();
                visibleVO.setObjectId(creatureUnit.getId());
                visibleVO.setType(creatureUnit.getType());
                visibleVO.setPosition(creatureUnit.getPosition());
                visibleVO.setVisibleName(creatureUnit.getVisibleName());
                visibleVOList.add(visibleVO);
            }
        }
        return visibleVOList;
    }

    @Override
    public Map<Integer, List<Position>> getVisiblePosition() {

        Map<Integer, List<Position>> positionMap= new HashMap<>();
        Map<String, FightAccount> fightAccounts = getFightAccounts();
        for(FightAccount fightAccount:fightAccounts.values()){
            if(fightAccount==null){
                continue;
            }
            Map<Long, CreatureUnit> creatureUnitMap = fightAccount.getCreatureUnitMap();
            for(CreatureUnit creatureUnit:creatureUnitMap.values()){
                List<Position> positions = positionMap.get(creatureUnit.getType().getTypeId());
                if(positions==null){
                    positions =  new ArrayList<>();
                    positionMap.put(creatureUnit.getType().getTypeId(),positions);
                }
                positions.add(creatureUnit.getPosition());
            }
        }
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

    /**
     * 做移动时的一些判断 玩家是否活的
     * @param accountId
     * @param targetpos
     */
    @Override
    public void move(String accountId, Position targetpos) {
        Map<String, FightAccount> fightAccounts = getFightAccounts();
        FightAccount fightAccount = fightAccounts.get(accountId);
        if(accountId==null){
            return;
        }
        Map<Long, CreatureUnit> creatureUnitMap = fightAccount.getCreatureUnitMap();
        for(CreatureUnit creatureUnit:creatureUnitMap.values()){
            if(creatureUnit.isDead()){
                //FIXME:看需求是否要显示给客户端看战斗单元死亡
                return;
            }
            if(creatureUnit!=null) {
                creatureUnit.setPosition(targetpos);
            }
        }
    }

    @Override
    public void leave(String accountId) {
        super.leave(accountId);
    }

    @Override
    public void enter(Player player) {
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

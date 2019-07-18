package com.game.scence.monster.service;

import com.game.SpringContext;
import com.game.role.skill.command.SkillCdCommand;
import com.game.role.skill.constant.SkillTargetType;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.model.SkillSlot;
import com.game.role.skill.packet.SM_SkillStatus;
import com.game.role.skill.packet.SM_UseSkillErr;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.field.model.FieldFightScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.monster.resource.MonsterResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;
import com.game.util.ComputeUtil;
import com.game.util.SendPacketUtil;
import com.game.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:13
 */
@Component
public class MonsterServiceImpl implements MonsterService {
    private static Logger logger = LoggerFactory.getLogger(MonsterServiceImpl.class);
    @Autowired
    private MonsterManager monsterManager;
    @Override
    public List<MonsterResource> getMapMonsterResources(int mapId){
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        List<Integer> monsterList = mapResource.getMonsterList();
        if(monsterList==null){
            if (logger.isDebugEnabled()) {
                logger.error("地图[{}]没有怪物", mapId);
            }
            return null;
        }
        List<MonsterResource> monsterResourceList = new ArrayList<>();
        for(int monsterId:monsterList){
            MonsterResource monsterResource = monsterManager.getMonsterResource(monsterId);
            if(monsterResource==null){
                continue;
            }
            monsterResourceList.add(monsterResource);
        }
        return monsterResourceList;
    }

    @Override
    public MonsterResource getMonsterResource(int monsterId) {

        return monsterManager.getMonsterResource(monsterId);
    }

    @Override
    public void fieldMonsterAttack(String accountId) {
        FieldFightScene scene = (FieldFightScene) SpringContext.getScenceSerivce().getScene(MapType.FIELD.getMapId());
        Map<Long, MonsterUnit> monsterUnits = scene.getMonsterUnits();
        for (MonsterUnit monsterUnit : monsterUnits.values()) {
            monsterUnit.doAttackAfter(monsterUnit.getAttacker());
        }
    }

}

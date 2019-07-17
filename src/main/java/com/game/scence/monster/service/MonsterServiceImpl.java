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
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.monster.resource.MonsterResource;
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

    public void monsterAttack(MonsterUnit monsterUnit, PlayerUnit targetUnit) {
        int mapId = monsterUnit.getMapId();
        SkillInfo skillInfo = monsterUnit.getSkillInfo();
        int defaultSkill = skillInfo.getDefaultSkill();
        Map<Integer, SkillSlot> skillSlotMap = skillInfo.getSkillSlotMap();
        SkillSlot skillSlot = skillSlotMap.get(defaultSkill);
        int level = skillSlot.getLevel();
        SkillResource skillResource = SpringContext.getSkillService().getSkillResource(defaultSkill);
        SkillLevelResource skillLevelResource = SpringContext.getSkillService().getSkillLevelResource(defaultSkill + StringUtil.XIA_HUA_XIAN + level);


        /**
         * 检查是否可以释放技能
         */
        if (!checkUseSkill(monsterUnit, skillResource, targetUnit)) {
            return;
        }

        /**
         * 做蓝量的消耗
         */
        long consume = skillLevelResource.getConsumeMp();
        if (!monsterUnit.consumeMpAndCheck(consume)) {
            return;
        }

        int[] effects = skillLevelResource.getEffects();
        for (int effectId : effects) {
            AbstractSkillEffect skillEffect = SpringContext.getEffectService().getSkillEffect(effectId);
            skillEffect.doActive(mapId, monsterUnit, targetUnit, skillLevelResource, skillResource);
            if (targetUnit.isDead()) {
                break;
            }
        }
        /**
         * 抛出技能cd的command
         */
        SkillCdCommand command = SkillCdCommand.valueOf(mapId, skillResource.getId(), skillLevelResource.getCd(), monsterUnit.getAccountId(), monsterUnit);
        SpringContext.getSceneExecutorService().submit(command);
        /**
         * 将command放在unit身上方便管理
         */
        monsterUnit.useSkillAfter(skillResource, skillLevelResource, monsterUnit);
        SendPacketUtil.send(monsterUnit.getAccountId(), SM_SkillStatus.valueOf(skillResource.getId(), 2));
    }

    private boolean checkUseSkill(CreatureUnit useUnit, SkillResource skillResource, CreatureUnit targetUnit) {
        // 使用技能者是否已死亡
        if (useUnit == null || useUnit.isDead()) {
            SendPacketUtil.send(useUnit.getAccountId(), SM_UseSkillErr.valueOf(6));
            return false;
        }
        // 目标是否死亡
        if (targetUnit == null || targetUnit.isDead()) {
            SendPacketUtil.send(useUnit.getAccountId(), SM_UseSkillErr.valueOf(1));
            logger.info("目标已死亡[{}]", targetUnit.getId());
            return false;
        }
        // 检查技能的目标类型
        String useType = skillResource.getTargetType();
        int skillModelId = skillResource.getId();
        SkillTargetType type = SkillTargetType.skillTargetTypeMap.get(useType);
        if (type == null) {
            return false;
        }
        // 检查怪物技能cd和是否学习该技能
        SkillInfo skillInfo = useUnit.getSkillInfo();
        if (useUnit.getSkillCdStatus(skillModelId) != null && useUnit.getSkillCdStatus(skillModelId)) {
            SendPacketUtil.send(useUnit.getAccountId(), SM_UseSkillErr.valueOf(7));
            logger.info("玩家[{}]技能[{}]处于cd状态", useUnit.getAccountId(), skillModelId);
            return false;
        }
        SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(skillModelId);
        if (!skillSlot.isCanUse()) {
            // 没有学习 无法使用
            SendPacketUtil.send(useUnit.getAccountId(), SM_UseSkillErr.valueOf(3));
            logger.info("玩家[{}]没有学习技能[{}]", useUnit.getAccountId(), skillModelId);
            return false;
        }
        if (skillModelId <= 0) {
            return false;
        }
        // 检查蓝量
        SkillLevelResource skillLevelResource = SpringContext.getSkillService().getSkillLevelResource(
                skillModelId + StringUtil.XIA_HUA_XIAN + skillSlot.getLevel());
        long currMp = useUnit.getCurrMp();
        if (skillLevelResource.getConsumeMp() > currMp) {
            // 蓝不足
            SendPacketUtil.send(useUnit.getAccountId(), SM_UseSkillErr.valueOf(8));
            return false;
        }

        //  检查施法距离
        double useDis = ComputeUtil.computeDis(targetUnit.getPosition(), useUnit.getPosition());
        if (useDis > skillLevelResource.getUseDis()) {
            SendPacketUtil.send(useUnit.getAccountId(), SM_UseSkillErr.valueOf(4));
            return false;
        }

        return true;
    }
}

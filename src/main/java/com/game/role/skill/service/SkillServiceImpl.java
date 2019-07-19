package com.game.role.skill.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.role.skill.command.SkillSynCommand;
import com.game.role.skill.command.UseSkillCommand;
import com.game.role.skill.constant.SkillTargetType;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.model.SkillSlot;
import com.game.role.skill.packet.*;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.context.SkillUseContext;
import com.game.role.skilleffect.context.SkillUseContextEnm;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.scence.visible.resource.MapResource;
import com.game.user.condition.model.LearnSkillCondition;
import com.game.user.condition.model.UpgradeSkillCondition;
import com.game.util.*;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 17:37
 */
@Component
public class SkillServiceImpl implements SkillService {
    private static final Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);
    @Autowired
    private SkillManager skillManager;

    @Override
    public SkillResource getSkillResource(int id) {
        return skillManager.getSkillResource(id);
    }

    @Override
    public SkillLevelResource getSkillLevelResource(String id) {
        return skillManager.getSkillLevelResource(id);
    }

    @Override
    public JobSkillResource getJobSkillResource(int jobType) {
        return skillManager.getJobSkillResource(jobType);
    }

    @Override
    public SkillEnt getSkillEnt(long playerId) {
        return skillManager.getOrCreateSkillEnt(playerId);
    }

    @Override
    public void saveSkill(SkillEnt skillEnt) {
        skillManager.save(skillEnt);
    }

    /**
     * @param skillId 技能唯一id
     */
    @Override
    public void learnSkill(int skillId, long playerId) {

        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        int skillLevel = 1;
        SkillLevelResource skillLevelResource = getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + skillLevel);
        String preSkillId = skillLevelResource.getPreSkillId();
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        SkillSlot learnSlot = skillInfo.getSkillSlotMap().get(skillId);
        if (learnSlot == null) {
            RequestException.throwException(I18nId.NOT_SKILL_IN_SLOT);
        }
        if (learnSlot.isCanUse()) {
            RequestException.throwException(I18nId.SKILL_IS_LEARN);
        }
        // 判断前置技能
        if (preSkillId != null) {
            String[] split = preSkillId.split(StringUtil.XIA_HUA_XIAN);
            SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(Integer.parseInt(split[0]));
            if (!skillSlot.isCanUse() || skillSlot.getLevel() < Integer.parseInt(split[1])) {
                logger.info("玩家[{}]角色[{}]没有学习前置技能[{}]无法学习[{}]", player.getAccountId(), playerId, preSkillId, skillId);
                RequestException.throwException(I18nId.SKILL_CONDITION_NOT);

            }
        }

        SkillResource skillResource = getSkillResource(skillId);
        if (skillResource == null) {
            logger.warn("资源[{}]为空", SkillResource.class.getSimpleName());
            return;
        }
        LearnSkillCondition learnSkillCondition = skillResource.getLearnSkillCondition();

        if (learnSkillCondition == null) {
            logger.warn("资源[{}]为空", LearnSkillCondition.class.getSimpleName());
            return;
        }
        if (!learnSkillCondition.checkCondition(player, null)) {
            logger.info("玩家[{}]角色[{}]学习技能[{}]条件不足", player.getAccountId(), playerId, skillId);
            RequestException.throwException(I18nId.SKILL_CONDITION_NOT);
        }
        learnSlot.setCanUse(true);
        learnSlot.setLevel(skillLevel);
        /**
         * 同步技能信息
         */
        SkillSynCommand command = SkillSynCommand.valueOf(player, skillInfo.deepCopy());
        SpringContext.getSceneExecutorService().submit(command);
        saveSkill(skillEnt);
        SendPacketUtil.send(player, SM_LearnSkill.valueOf(1));
    }

    @Override
    public void upgradeSkill(long playerId, int skillId) {
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        Map<Integer, SkillSlot> skillSlotMap = skillInfo.getSkillSlotMap();
        if (skillSlotMap == null) {
            return;
        }
        SkillResource skillResource = getSkillResource(skillId);
        SkillSlot skillSlot = skillSlotMap.get(skillId);
        if (!skillSlot.isCanUse()) {
            logger.info("角色[{}]未学习技能[{}]", playerId, skillId);
            RequestException.throwException(I18nId.NOT_UPGRADE_NOT_LEARN);
        }
        if (skillResource.getMaxLevel() <= skillSlot.getLevel()) {
            logger.info("角色[{}]的技能[{}]达到最大等级无法升级", playerId, skillId);
            RequestException.throwException(I18nId.SKILL_LEVEL_LIMINT);
        }
        int level = skillSlot.getLevel();
        SkillLevelResource skillLevelResource = getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + level);
        UpgradeSkillCondition upgradeSkillCondition = skillLevelResource.getUpgradeSkillCondition();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        if (!upgradeSkillCondition.checkCondition(player, null)) {
            logger.info("角色[{}]的技能[{}]升级条件不足", playerId, skillId);
            RequestException.throwException(I18nId.SKILL_UPGRADE_INSUFFICIENT);

        }
        skillSlot.setLevel(level + 1);
        /**
         * 同步技能信息
         */
        SkillSynCommand command = SkillSynCommand.valueOf(player, skillInfo.deepCopy());
        SpringContext.getSceneExecutorService().submit(command);
        SendPacketUtil.send(playerId, SM_UpgradeSkill.valueOf(1));
        saveSkill(skillEnt);
    }

    @Override
    public void showSkillInfo(long playerId) {
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        Map<Integer, SkillSlot> skillSlotMap = skillInfo.getSkillSlotMap();

        SM_ShowSkillInfo sm = new SM_ShowSkillInfo();
        sm.setPlayerId(playerId);
        sm.setSkillSlotMap(skillSlotMap);
        SendPacketUtil.send(playerId, sm);
    }

    @Override
    public void showSkillBar(long playerId) {
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        Map<Integer, Integer> skillBarMap = skillInfo.getSkillBarMap();
        SM_ShowSkillBar sm = new SM_ShowSkillBar();
        sm.setPlayerId(playerId);
        sm.setSkillBarMap(skillBarMap);
        SendPacketUtil.send(playerId, sm);
    }

    @Override
    public void setSkillBar(TSession session, long playerId, String setStr) {
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        Map<Integer, SkillSlot> skillSlotMap = skillInfo.getSkillSlotMap();
        Map<Integer, Integer> skillBarMap = skillInfo.getSkillBarMap();
        String[] strs = setStr.split(",");
        /**
         * 判断快捷技能栏的大小
         */
        if (strs.length > PlayerUtil.SKILL_SLOT_NUM) {
            session.sendPacket(SM_SetSkillBar.valueOf(2));
            return;
        }
        for (int i = 1; i <= strs.length; i++) {
            /**
             * 判断是否有技能
             */
            int skillId = Integer.parseInt(strs[i - 1]);
            if (skillId == -1) {
                skillBarMap.put(i, -1);
                continue;
            }
            SkillSlot skillSlot = skillSlotMap.get(skillId);
            if (skillSlot == null) {
                skillBarMap.put(i, -1);
                continue;
            }
            if (!skillSlot.isCanUse()) {
                skillBarMap.put(i, -1);
                continue;
            }
            skillBarMap.put(i, skillId);
        }
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        /**
         * 同步技能信息
         */
        SkillSynCommand command = SkillSynCommand.valueOf(player, skillInfo);
        SpringContext.getSceneExecutorService().submit(command);
        saveSkill(skillEnt);
        session.sendPacket(SM_SetSkillBar.valueOf(1));
    }

    @Override
    public void useSkill(TSession session, int mapId, int sceneId, long targetId, long useId, ObjectType useType, int skillBarId, ObjectType targetType) {
        UseSkillCommand useSkillCommand = UseSkillCommand.valueOf(session.getAccountId(), mapId, targetId, useId, useType, skillBarId, targetType);
        SpringContext.getSceneExecutorService().submit(useSkillCommand);
    }


    @Override
    public void doUseSkill(String accountId, int mapId, long useId, ObjectType useType, long targetId, ObjectType targetType, int skillBarId) {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        if (scene == null) {
            return;
        }
        CreatureUnit useUnit = scene.getUnit(useType, useId);
        CreatureUnit targetUnit = scene.getUnit(targetType, targetId);
        if (useUnit == null) {
            return;
        }
        SkillInfo skillInfo = useUnit.getSkillInfo();
        SkillResource skillResource = skillInfo.getSkillResource(skillBarId);


        // 检查是否可以释放技能
        if (!checkUseSkill(useUnit, skillResource, targetUnit)) {
            return;
        }
        SkillLevelResource skillLevelResource = skillInfo.getSkillLevelResource(skillBarId);
        if (skillLevelResource == null) {
            RequestException.throwException(I18nId.NOT_SKILL_IN_SLOT);
        }
        // TODO: 2019/7/19 doUseSkillBefore应该实现一些技能在触发之前的特殊效果或者，如放技能前可能需要蓄力，触发霸体效果等
        // TODO: 2019/7/19 做蓝量的消耗，使用技能之前固定回血等效果
        // 本游戏还比较简单 暂时不做这些 只做一些基本的消耗
        long consume = skillLevelResource.getConsumeMp();
        if (!useUnit.consumeMpAndCheck(consume)) {
            return;
        }
        SkillUseContext skillUseContext = new SkillUseContext();
        skillUseContext.putSkillContextEnum(SkillUseContextEnm.SKILL_ATTACKER, useUnit);
        skillUseContext.putSkillContextEnum(SkillUseContextEnm.SKILL_RESOURCE, skillResource);
        skillUseContext.putSkillContextEnum(SkillUseContextEnm.SKILL_LEVEL_RESOURCE, skillLevelResource);

        List<CreatureUnit> targetUnits = getTargetUnits(mapId, scene, useUnit, targetUnit, skillLevelResource);
        // 使用技能
        useUnit.useSkill(skillUseContext, targetUnits);
        /**
         * 将command放在unit身上方便管理
         */
        useUnit.useSkillAfter(skillResource, skillLevelResource, useUnit);
        SendPacketUtil.send(useUnit.getAccountId(), SM_SkillStatus.valueOf(skillResource.getId(), 2));
        /**
         * 设置怪物的最后一次攻击者
         */
        for (CreatureUnit unit : targetUnits) {
            if (unit.getType() == ObjectType.MONSTER && useUnit.getType() == ObjectType.PLAYER) {
                unit.setAttacker(useUnit);
            }
        }
    }

    private List<CreatureUnit> getTargetUnits(int mapId, AbstractScene scene, CreatureUnit useUnit, CreatureUnit targetUnit, SkillLevelResource skillLevelResource) {
        /**
         * 可多目标
         * fixme：这里先暂时遍历地图中所有目标类型的生物，看是否在技能范围内，之后再想有没有更好的办法
         */
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        List<ObjectType> targetTypes = mapResource.getTargetTypes();
        if (targetTypes == null) {
            return null;
        }
        /**
         * 计算目标范围的怪物集合  fixme: 这里如果每次使用单目标的技能 总会去查找所该类型所以的生物
         */
        List<VisibleVO> visibleVOList = scene.getVisibleVOList();
        int targetNum = skillLevelResource.getTargetNum();
        List<CreatureUnit> targetUnits = new ArrayList<>();
        if (targetNum == 1) {
            targetUnits.add(targetUnit);
        } else {
            for (VisibleVO visibleVO : visibleVOList) {
                if (!targetTypes.contains(visibleVO.getType())) {
                    continue;
                }
                if (!checkRange(visibleVO, skillLevelResource.getUseRangeRadius(), targetUnit.getPosition())) {
                    continue;
                }
                CreatureUnit effectCreatureUnit = scene.getUnit(visibleVO.getType(), visibleVO.getObjectId());
                if (!effectCreatureUnit.isDead() && !(effectCreatureUnit.getType() == useUnit.getType() && effectCreatureUnit.getId() == useUnit.getId())) {
                    targetUnits.add(effectCreatureUnit);
                }
            }
        }
        return targetUnits;
    }

    public boolean checkRange(VisibleVO visibleVO, long useRangeRadius, Position position) {
        double dis = ComputeUtil.computeDis(visibleVO.getPosition(), position);
        if (dis <= useRangeRadius) {
            return true;
        }
        return false;
    }
    private boolean checkUseSkill(CreatureUnit useUnit, SkillResource skillResource, CreatureUnit targetUnit) {

        // 使用技能者是否已死亡
        if (useUnit.isDead()) {
            RequestException.throwException(I18nId.DEAD_NOT_USE_SKILL);
        }
        if (targetUnit == null) {
            RequestException.throwException(I18nId.MAP_NOT_TARGET);
        }
        // 目标是否死亡
        if (targetUnit.isDead()) {
            RequestException.throwException(I18nId.TARGET_DEAD);
        }
        // 检查技能栏是否有技能
        if (skillResource == null) {
            RequestException.throwException(I18nId.NOT_USE_SKILL_NOT_SKILL);
        }
        // 检查技能的目标类型
        String useType = skillResource.getTargetType();
        int skillModelId = skillResource.getId();
        SkillTargetType type = SkillTargetType.skillTargetTypeMap.get(useType);
        if (type == null) {
            return false;
        }
        int mapId = useUnit.getMapId();
        // 判断地图中技能的使用限制
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        if (type != SkillTargetType.MYSELF) {
            List<ObjectType> targetList = mapResource.getTargetTypes();
            if (targetList == null) {
                logger.info("地图[{}]不可以释放技能", mapId);
                RequestException.throwException(I18nId.MAP_NOT_USE_SKILL);
            }
            if (!targetList.contains(targetUnit.getType())) {
                RequestException.throwException(I18nId.MAP_NOT_USE_SKILL);
            }
        }
        // 检查玩家技能cd
        SkillInfo skillInfo = useUnit.getSkillInfo();
        if (useUnit.getSkillCdStatus(skillModelId) != null && useUnit.getSkillCdStatus(skillModelId)) {
            logger.info("玩家[{}]技能[{}]处于cd状态", useUnit.getAccountId(), skillModelId);
            RequestException.throwException(I18nId.SKILL_CD);
        }
        SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(skillModelId);
        // 是否学习该技能
        if (!skillSlot.isCanUse()) {
            // 没有学习 无法使用
            logger.info("玩家[{}]没有学习技能[{}]", useUnit.getAccountId(), skillModelId);
            RequestException.throwException(I18nId.NOT_LEARN_SKILL);
        }
        if (skillModelId <= 0) {
            return false;
        }
        //判断该场景下能否使用技能
        if (!scene.isCanUseSkill()) {
            RequestException.throwException(I18nId.MAP_NOT_USE_SKILL);
        }
        // 检查蓝量
        SkillLevelResource skillLevelResource = getSkillLevelResource(skillModelId + StringUtil.XIA_HUA_XIAN + skillSlot.getLevel());
        long currMp = useUnit.getCurrMp();
        if (skillLevelResource.getConsumeMp() > currMp) {
            // 蓝不足
            RequestException.throwException(I18nId.MP_NOT_ENOUGH);
        }

        //  检查施法距离
        double useDis = ComputeUtil.computeDis(targetUnit.getPosition(), useUnit.getPosition());
        if (useDis > skillLevelResource.getUseDis()) {
            RequestException.throwException(I18nId.TARGET_TO_LONG);
        }

        return true;
    }
}

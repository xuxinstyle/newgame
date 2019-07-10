package com.game.role.skill.service;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.role.skill.command.ReviveCreatureCommand;
import com.game.role.skill.command.UseSkillCommand;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.model.SkillSlot;
import com.game.role.skill.packet.*;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.field.model.FieldFightScene;
import com.game.scence.fight.constant.HurtType;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.FightAccount;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.event.CreatureDeadEvent;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.user.condition.model.LearnSkillCondition;
import com.game.user.condition.model.UpgradeSkillCondition;
import com.game.util.ComputeUtil;
import com.game.util.PlayerUtil;
import com.game.util.SendPacketUtil;
import com.game.util.StringUtil;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
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
        /**
         * 1 check玩家是否能学习该技能 判断前置技能
         * 2.消耗物品学习技能
         * 3.如果能学习，则将该技能设置为可使用状态 并设置技能的等级
         * 4.通知客户端
         */
        int skillLevel = 1;
        SkillLevelResource skillLevelResource = getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + skillLevel);
        String preSkillId = skillLevelResource.getPreSkillId();
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        if (preSkillId != null) {
            String[] split = preSkillId.split(StringUtil.XIA_HUA_XIAN);
            SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(split[0]);
            if (!skillSlot.isCanUse()) {
                SendPacketUtil.send(player, SM_LearnSkill.valueOf(2));
                logger.error("玩家[{}]角色[{}]没有学习前置技能[{}]无法学习[{}]", player.getAccountId(), playerId, preSkillId, skillId);
                return;
            }
        }

        SkillResource skillResource = getSkillResource(skillId);
        LearnSkillCondition learnSkillCondition = skillResource.getLearnSkillCondition();
        if (learnSkillCondition != null) {
            if (!skillResource.getLearnSkillCondition().checkCondition(player, null)) {
                SendPacketUtil.send(player, SM_LearnSkill.valueOf(2));
                logger.error("玩家[{}]角色[{}]学习技能[{}]失败", player.getAccountId(), playerId, skillId);
                return;
            }
        }

        SkillSlot learnSlot = skillInfo.getSkillSlotMap().get(skillId);
        learnSlot.setCanUse(true);
        learnSlot.setLevel(skillLevel);
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
            SendPacketUtil.send(playerId, SM_UpgradeSkill.valueOf(2));
            logger.error("角色[{}]未学习技能[{}]", playerId, skillId);
            return;
        }
        if (skillResource.getMaxLevel() <= skillSlot.getLevel()) {
            SendPacketUtil.send(playerId, SM_UpgradeSkill.valueOf(2));
            logger.error("角色[{}]的技能[{}]达到最大等级无法升级", playerId, skillId);
            return;
        }
        int level = skillSlot.getLevel();
        SkillLevelResource skillLevelResource = getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + level);
        UpgradeSkillCondition upgradeSkillCondition = skillLevelResource.getUpgradeSkillCondition();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        if (!upgradeSkillCondition.checkCondition(player, null)) {
            SendPacketUtil.send(playerId, SM_UpgradeSkill.valueOf(2));
            logger.error("角色[{}]的技能[{}]升级条件不足", playerId, skillId);
            return;
        }
        skillSlot.setLevel(level + 1);
        SendPacketUtil.send(playerId, SM_UpgradeSkill.valueOf(1));
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
        if(strs.length>PlayerUtil.SKILL_SLOT_NUM){
            session.sendPacket(SM_SetSkillBar.valueOf(2));
            return;
        }
        for (int i = 1; i <= strs.length; i++) {
            /**
             * 判断是否有被设置的技能
             */
            int skillId = Integer.parseInt(strs[i-1]);
            if(skillId==-1){
                skillBarMap.put(i,-1);
                continue;
            }
            SkillSlot skillSlot = skillSlotMap.get(skillId);
            if (skillSlot == null) {
                skillBarMap.put(i,-1);
                continue;
            }
            if(!skillSlot.isCanUse()){
                skillBarMap.put(i,-1);
                continue;
            }
            skillBarMap.put(i,skillId);
        }
        saveSkill(skillEnt);
        session.sendPacket(SM_SetSkillBar.valueOf(1));
    }

    @Override
    public void useSkillToMonster(TSession session, int mapId, int sceneId, long targetId, long useId, int skillBarId) {
        UseSkillCommand useSkillCommand = UseSkillCommand.valueOf(mapId, sceneId, session.getAccountId(), skillBarId, targetId, useId);
        SpringContext.getSceneExecutorService().submit(useSkillCommand);

    }

    /**
     * FIXME:这里先试着写 这里只适用于FieldFightScene地图
     * @param accountId
     * @param mapId
     * @param sceneId
     * @param skillBarId 技能栏id
     * @param targetId
     * @param useId
     */
    @Override
    public void doUseSkill(String accountId,int mapId, int sceneId, int skillBarId, long targetId, long useId){
        try {
            /**
             * 检查场景中是否有玩家
             */
            AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
            Map<String, FightAccount> fightAccounts = scene.getFightAccounts();
            if (fightAccounts == null) {
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(2));
                return;
            }
            FightAccount fightAccount = fightAccounts.get(accountId);
            if (fightAccount == null) {
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(2));
                return;
            }
            Map<Long, CreatureUnit> creatureUnitMap = fightAccount.getCreatureUnitMap();
            if (creatureUnitMap == null) {
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(2));
                return;
            }
            CreatureUnit creatureUnit = creatureUnitMap.get(useId);
            if (creatureUnit == null) {
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(2));
                return;
            }
            if(creatureUnit.isDead()){
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(6));
                return;
            }
            if(creatureUnit.isSkillCd()){
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(7));
                return;
            }
            /**
             * 检查玩家是否能用技能技能
             */
            SkillInfo skillInfo = creatureUnit.getSkillInfo();
            int skillId = skillInfo.getSkillBarMap().get(skillBarId);
            SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(skillId);
            if (!skillSlot.isCanUse()) {
                /**
                 * 没有学习 无法使用
                 */
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(3));
                return;
            }
            if (skillId <= 0) {
                return;
            }
            /**
             * FIXME:这里先强转 加标识判断场景能否使用技能
             */
            if(!scene.isCanUseSkill()){
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(5));
                return;
            }
            /**
             * 获取怪物 这里先写死，如果加了其他可以释放技能的地图，则应该使用重载
             */
            FieldFightScene fieldFightScene = (FieldFightScene) scene;
            Map<Long, MonsterUnit> monsterUnits = fieldFightScene.getMonsterUnits();
            MonsterUnit monsterUnit = monsterUnits.get(targetId);
            if (monsterUnit == null) {
                SendPacketUtil.send(accountId, SM_UseSkillToMonsterErr.valueOf(2));
                return;
            }
            SkillLevelResource skillLevelResource = getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + skillSlot.getLevel());
            SkillResource skillResource = getSkillResource(skillId);
            /**
             * 检查施法距离
             */
            double useDis = ComputeUtil.computeDis(monsterUnit.getPosition(), creatureUnit.getPosition());
            if(useDis>skillLevelResource.getUseDis()){
                SendPacketUtil.send(accountId,SM_UseSkillToMonsterErr.valueOf(4));
                return;
            }
            /**
             * fixme：这里先暂时遍历地图中所有的玩家，看是否在技能范围内，之后再想有没有更好的办法
             */
            List<CreatureUnit> creatureUnits = new ArrayList<>();
            List<VisibleVO> visibleVOList = fieldFightScene.getVisibleVOList();
            /**
             * 计算目标范围的生物集合
             */
            for (VisibleVO visibleVO : visibleVOList) {
                if (visibleVO.getType() != ObjectType.MONSTER) {
                    continue;
                }
                if (!checkRange(visibleVO, skillLevelResource.getUseRangeRadius(), monsterUnit.getPosition())) {
                    continue;
                }
                creatureUnits.add(monsterUnits.get(visibleVO.getObjectId()));
            }
            /**
             * 实际计算使用技能的效果
             */
            doRealUseSkill(mapId,creatureUnit, creatureUnits, skillLevelResource, skillResource);
        }catch (Exception e){
            logger.error("使用技能错误",e);
        }

    }

    private boolean checkRange(VisibleVO visibleVO, long useRangeRadius,Position position) {
        double dis = ComputeUtil.computeDis(visibleVO.getPosition(),position);
        if(dis<=useRangeRadius){
            return true;
        }
        return false;
    }


    /**
     * 真实计算技能伤害的地方
     * @param useUnit
     * @param targetUnits
     * @param skillLevelResource
     * @param skillResource
     */
    private void doRealUseSkill(int mapId,CreatureUnit useUnit, List<CreatureUnit> targetUnits, SkillLevelResource skillLevelResource, SkillResource skillResource) {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        /**
         * 单目标
         */
        HurtType hurtType = HurtType.valueOf(skillLevelResource.getHurtType());
        AttributeType attrType = hurtType.getHurtAttributeType();
        CreatureAttributeContainer attributeContainer = useUnit.getAttributeContainer();
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        Attribute attribute = finalAttributes.get(attrType);
        /**
         * 减少使用者的蓝量
         */
        long consume = skillLevelResource.getConsume();
        useUnit.setCurrMp(useUnit.getCurrMp()-consume);
        /**
         * 技能固定伤害
         */
        long hurt = skillLevelResource.getHurt();
        /**
         * 技能属性加成伤害
         */
        long prop = skillLevelResource.getProp();

        double hurtValue = ComputeUtil.getHurtValue(attribute.getValue(), hurt, prop);
        /**
         * 获取该伤害类型对应的防御属性类型 物理防御或法术防御
         */
        AttributeType defenceAttributeType = hurtType.getDefenceAttributeType();
        Map<Long ,Long> realHurtMap = new HashMap<>();
        for(CreatureUnit targetUnit:targetUnits){
            Map<AttributeType, Attribute> targetFinalAttributes = targetUnit.getAttributeContainer().getFinalAttributes();
            Attribute defAttribute = targetFinalAttributes.get(defenceAttributeType);
            /**
             * 计算真实扣血量
             */
            long realHurt = (long)ComputeUtil.getRealHurt(hurtValue, defAttribute.getValue());
            long currHp = targetUnit.getCurrHp();
            if(currHp-realHurt<=0){
                realHurtMap.put(targetUnit.getId(),currHp);
                targetUnit.setCurrHp(0);
                targetUnit.setDead(true);
                CreatureDeadEvent event = CreatureDeadEvent.valueOf(targetUnit,mapId);
                SpringContext.getEvenManager().syncSubmit(event);
            }else {
                targetUnit.setCurrHp((currHp - realHurt));
                realHurtMap.put(targetUnit.getId(), realHurt);
            }
        }


        SM_UseSkillToMonster sm = new SM_UseSkillToMonster();
        sm.setSkillId(skillResource.getId());
        sm.setUnitId(useUnit.getId());
        sm.setUnitName(useUnit.getVisibleName());
        sm.setRealHurtMap(realHurtMap);

        List<String> accountIds = scene.getAccountIds();
        for (String accountId:accountIds) {
            SendPacketUtil.send(accountId,sm);
        }

    }

    /**
     * 定时复活
     * @param mapId
     * @param creatureUnit
     */
    @Override
    public void doCreatureDeadAfter(int mapId, CreatureUnit creatureUnit) {

        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        List<String> accountIds = scene.getAccountIds();
        for(String accountId:accountIds){
            SendPacketUtil.send(accountId,SM_CreatureDead.valueOf(mapId,creatureUnit));
        }
        /**
         * 抛一个定时器 定时复活怪物
         */
        ReviveCreatureCommand command = ReviveCreatureCommand.valueOf(mapId,creatureUnit.getReviveCd(),creatureUnit.getAccountId(),creatureUnit);
        creatureUnit.putCommand(command);
        SpringContext.getSceneExecutorService().submit(command);

    }

    /**
     * 复活场景中的生物
     * @param mapId
     * @param creatureUnit
     */
    @Override
    public void doCreatureRevive(int mapId, CreatureUnit creatureUnit) {
        creatureUnit.setCurrHp(creatureUnit.getMaxHp());
        creatureUnit.setCurrMp(creatureUnit.getMaxMp());
        creatureUnit.setDead(false);
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        List<String> accountIds = scene.getAccountIds();
        SM_CreatureRevive sm = SM_CreatureRevive.valueOf(creatureUnit);
        for(String accountId:accountIds){
            SendPacketUtil.send(accountId,sm);
        }
    }
}

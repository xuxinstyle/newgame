package com.game.role.skill.service;

import com.game.SpringContext;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.model.SkillSlot;
import com.game.role.skill.packet.SM_LearnSkill;
import com.game.role.skill.packet.SM_ShowSkillBar;
import com.game.role.skill.packet.SM_UpgradeSkill;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.user.condition.model.LearnSkillCondition;
import com.game.user.condition.model.UpgradeSkillCondition;
import com.game.util.SendPacketUtil;
import com.game.util.StringUtil;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public SkillEnt getSkillEnt(long playerId){
        return skillManager.getOrCreateSkillEnt(playerId);
    }

    @Override
    public void saveSkill(SkillEnt skillEnt){
        skillManager.save(skillEnt);
    }

    /**
     * @param skillId 技能唯一id
     *
     */
    @Override
    public void learnSkill(int skillId,long playerId){
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
        if(preSkillId!=null) {
            String[] split = preSkillId.split(StringUtil.XIA_HUA_XIAN);
            SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(split[0]);
            if(!skillSlot.isCanUse()){
                SendPacketUtil.send(player,SM_LearnSkill.valueOf(2));
                logger.error("玩家[{}]角色[{}]没有学习前置技能[{}]无法学习[{}]",player.getAccountId(),playerId,preSkillId,skillId);
                return;
            }
        }

        SkillResource skillResource = getSkillResource(skillId);
        LearnSkillCondition learnSkillCondition = skillResource.getLearnSkillCondition();
        if(learnSkillCondition!=null){
            if(!skillResource.getLearnSkillCondition().checkCondition(player,null)){
                SendPacketUtil.send(player,SM_LearnSkill.valueOf(2));
                logger.error("玩家[{}]角色[{}]学习技能[{}]失败",player.getAccountId(),playerId,skillId);
                return;
            }
        }

        SkillSlot learnSlot = skillInfo.getSkillSlotMap().get(skillId);
        learnSlot.setCanUse(true);
        learnSlot.setLevel(skillLevel);
        saveSkill(skillEnt);
        SendPacketUtil.send(player,SM_LearnSkill.valueOf(1));
    }

    @Override
    public void upgradeSkill(long playerId, int skillId){
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        Map<Integer, SkillSlot> skillSlotMap = skillInfo.getSkillSlotMap();
        if(skillSlotMap==null){
            return;
        }
        SkillResource skillResource = getSkillResource(skillId);

        SkillSlot skillSlot = skillSlotMap.get(skillId);
        if(!skillSlot.isCanUse()){
            SendPacketUtil.send(playerId,SM_UpgradeSkill.valueOf(2));
            logger.error("角色[{}]未学习技能[{}]",playerId,skillId);
            return;
        }
        if(skillResource.getMaxLevel()<=skillSlot.getLevel()){
            SendPacketUtil.send(playerId,SM_UpgradeSkill.valueOf(2));
            logger.error("角色[{}]的技能[{}]达到最大等级无法升级",playerId,skillId);
            return;
        }
        int level = skillSlot.getLevel();
        SkillLevelResource skillLevelResource = getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + level);
        UpgradeSkillCondition upgradeSkillCondition = skillLevelResource.getUpgradeSkillCondition();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        if(!upgradeSkillCondition.checkCondition(player,null)){
            SendPacketUtil.send(playerId,SM_UpgradeSkill.valueOf(2));
            logger.error("角色[{}]的技能[{}]升级条件不足",playerId,skillId);
            return;
        }
        skillSlot.setLevel(level+1);
        SendPacketUtil.send(playerId,SM_UpgradeSkill.valueOf(1));
    }

    @Override
    public void showSkillBar(long playerId){
        SkillEnt skillEnt = getSkillEnt(playerId);
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        Map<Integer, SkillSlot> skillSlotMap = skillInfo.getSkillSlotMap();
        SM_ShowSkillBar sm = new SM_ShowSkillBar();
        sm.setPlayerId(playerId);
        sm.setSkillSlotMap(skillSlotMap);
        SendPacketUtil.send(playerId,sm);
    }
}

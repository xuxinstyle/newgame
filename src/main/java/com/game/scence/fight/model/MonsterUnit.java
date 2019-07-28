package com.game.scence.fight.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeIdEnum;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.model.SkillSlot;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.drop.command.DropItemAddCommand;
import com.game.scence.monster.resource.MonsterResource;
import com.game.world.hopetower.event.MonsterDeadEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 12:19
 */
public class MonsterUnit extends CreatureUnit {
    /**
     * 怪物对应的配置表
     */
    private MonsterResource monsterResource;

    /**
     * 生成怪物战斗单元
     * 生成属性
     * @param monsterResource
     * @return
     */
    public static MonsterUnit valueOf(MonsterResource monsterResource){
        MonsterUnit monsterUnit = new MonsterUnit();
        monsterUnit.setMonsterResource(monsterResource);
        /**
         * 技能
         */
        SkillInfo skillInfo = new SkillInfo();
        int[] skillIds = monsterResource.getSkillIds();
        Map<Integer, SkillSlot> skillSlotMap = new HashMap<>();
        for (int i = 0; i < skillIds.length; i++) {
            skillSlotMap.put(skillIds[i], SkillSlot.valueOf(skillIds[i], 1, true));
        }
        skillInfo.setSkillSlotMap(skillSlotMap);
        Map<Integer, Integer> skillBarMap = new HashMap<>();
        skillBarMap.put(1, skillIds[0]);
        skillInfo.setSkillBarMap(skillBarMap);
        skillInfo.setDefaultSkill(1);
        monsterUnit.setSkillInfo(skillInfo);
        /**
         * 属性
         */
        List<Attribute> attrs = monsterResource.getAttrs();
        monsterUnit.setAttributeContainer(new CreatureAttributeContainer());
        CreatureAttributeContainer attributeContainer = monsterUnit.getAttributeContainer();
        attributeContainer.putAndComputeAttributes(AttributeIdEnum.BASE,attrs);
        Attribute maxHp = attributeContainer.getFinalAttributes().get(AttributeType.MAX_HP);
        Attribute maxMp = attributeContainer.getFinalAttributes().get(AttributeType.MAX_MP);
        /**
         * 这里需要配置表配最大血量的属性，配置表已经配了最大血量了，肯定不为空
         */
        monsterUnit.setCurrHp(maxHp.getValue());
        monsterUnit.setCurrMp(maxMp.getValue());
        monsterUnit.setVisibleName(monsterResource.getName());
        monsterUnit.setType(ObjectType.MONSTER);
        monsterUnit.setDead(false);

        return monsterUnit;
    }

    @Override
    public void doAttackAfter(String accountId, CreatureUnit attacker) {
        // TODO: 2019/7/17 怪物反击 暂时不做
        if (attacker == null) {
            return;
        }
        SkillInfo skillInfo = getSkillInfo();
        int defaultSkill = skillInfo.getDefaultSkill();
        AbstractScene scene = getScene();
        if (scene == null) {
            return;
        }
        SpringContext.getSkillService().useSkill(accountId, scene.getMapId(), scene.getSceneId(), attacker.getId(), this.getId(), getType(), defaultSkill, attacker.getType());
    }

    /**
     * 做掉落物处理
     *
     * @param attacker
     */
    @Override
    public void doDropHandle(CreatureUnit attacker) {
        if (attacker.getType() != ObjectType.PLAYER) {
            return;
        }
        PlayerUnit unit = (PlayerUnit) attacker;
        // 抛回账号线程加道具到背包
        DropItemAddCommand command = DropItemAddCommand.valueOf(unit.getAccountId(), getMonsterResource(), unit.getJobId());
        SpringContext.getAccountExecutorService().submit(command);
    }

    @Override
    public void afterDead(CreatureUnit attacker) {
        super.afterDead(attacker);
        if (getType() == ObjectType.MONSTER) {
            MonsterResource monsterResource = getMonsterResource();
            MonsterDeadEvent event = MonsterDeadEvent.valueOf(attacker, monsterResource.getId());
            SpringContext.getEvenManager().syncSubmit(event);
        }
    }

    @Override
    public long getReviveCd() {
        return monsterResource.getReviveCd();
    }

    public MonsterResource getMonsterResource() {
        return monsterResource;
    }

    public void setMonsterResource(MonsterResource monsterResource) {
        this.monsterResource = monsterResource;
    }
}

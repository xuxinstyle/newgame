package com.game.role.skilleffect.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.role.skill.packet.SM_UseSkill;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.resource.SkillEffectResource;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.scence.visible.resource.MapResource;
import com.game.util.ComputeUtil;
import com.game.util.SendPacketUtil;
import com.game.util.StringUtil;

import java.util.*;

/**
 * 物理伤害效果
 *
 * @Author：xuxin
 * @Date: 2019/7/11 20:09
 */
public class PhysicalDamageEffect extends AbstractSkillEffect {
    /**
     * 固定伤害
     */
    private long fixedDamage;
    /**
     * 攻击力加成伤害
     */
    private long attackDamageRatio;

    @Override
    public void init(SkillEffectResource skillEffectResource) {
        super.init(skillEffectResource);
        String effectValue = skillEffectResource.getEffectValue();
        String[] split = effectValue.split(StringUtil.XIA_HUA_XIAN);
        fixedDamage = Long.parseLong(split[0]);
        attackDamageRatio = Long.parseLong(split[1]);
    }

    public long computeRealHurt(CreatureUnit useUnit, CreatureUnit targetUnit) {
        Attribute useAttackAttribute = useUnit.getAttributeContainer().getFinalAttributes().get(AttributeType.PHYSICAL_ATTACK);
        Attribute targetDefenceAttribute = targetUnit.getAttributeContainer().getFinalAttributes().get(AttributeType.PHYSICAL_DEFENSE);
        double hurtValue = ComputeUtil.getHurtValue(useAttackAttribute.getValue(), fixedDamage, attackDamageRatio);
        return (long) ComputeUtil.getRealHurt(hurtValue, targetDefenceAttribute.getValue());
    }

    public boolean checkRange(VisibleVO visibleVO, long useRangeRadius, Position position) {
        double dis = ComputeUtil.computeDis(visibleVO.getPosition(), position);
        if (dis <= useRangeRadius) {
            return true;
        }
        return false;
    }

    @Override
    public void doActive(int mapId, CreatureUnit useUnit, CreatureUnit targetUnit, SkillLevelResource skillLevelResource, SkillResource skillResource) {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        List<VisibleVO> visibleVOList = scene.getVisibleVOList();
        /**
         * 可多目标
         * fixme：这里先暂时遍历地图中所有目标类型的生物，看是否在技能范围内，之后再想有没有更好的办法
         */
        List<CreatureUnit> effectCreatureUnits = new ArrayList<>();

        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        List<String> targetTypes = mapResource.getTargetTypes();
        if (targetTypes == null) {
            return;
        }
        /**
         * 计算目标范围的怪物集合  fixme: 这里如果每次使用单目标的技能 总会去查找所该类型所以的生物
         */
        for (VisibleVO visibleVO : visibleVOList) {
            if (!targetTypes.contains(visibleVO.getType().name())) {
                continue;
            }
            if (!checkRange(visibleVO, skillLevelResource.getUseRangeRadius(), targetUnit.getPosition())) {
                continue;
            }
            CreatureUnit effectCreatureUnit = scene.getUnit(visibleVO.getType(), visibleVO.getObjectId());
            if (!effectCreatureUnit.isDead()) {
                effectCreatureUnits.add(effectCreatureUnit);
            }

        }

        Map<String, Long> creatureUnitHurtMap = new HashMap<>();
        /**
         * 计算真实扣血量
         */
        for (CreatureUnit creatureUnit : effectCreatureUnits) {
            long realHurt = computeRealHurt(useUnit, targetUnit);
            String key = creatureUnit.getType().getTypeId() + StringUtil.XIA_HUA_XIAN + creatureUnit.getId();
            creatureUnitHurtMap.put(key, realHurt);
            /**
             * 做减血操作，如果死了就做死亡处理
             */
            creatureUnit.consumeHpAndDoDead(realHurt, useUnit);
        }


    }

    public long getAttackDamageRatio() {
        return attackDamageRatio;
    }

    public void setAttackDamageRatio(long attackDamageRatio) {
        this.attackDamageRatio = attackDamageRatio;
    }

    public long getFixedDamage() {
        return fixedDamage;
    }

    public void setFixedDamage(long fixedDamage) {
        this.fixedDamage = fixedDamage;
    }
}

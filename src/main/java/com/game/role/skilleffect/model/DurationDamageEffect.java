package com.game.role.skilleffect.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.attributeid.SkillEffectAttributeId;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.executor.ICommand;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.command.SkillDurationDamageCommand;
import com.game.role.skilleffect.resource.SkillEffectResource;
import com.game.scence.fight.model.CreatureUnit;
import com.game.util.ComputeUtil;
import com.game.util.StringUtil;
import com.game.util.TimeUtil;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 10:45
 */
public class DurationDamageEffect extends AbstractSkillEffect {
    /**
     * 单次造成的固定伤害
     */
    private long fixedHurt;
    /**
     * 单次造成法术伤害的百分比
     */
    private long hurtProp;
    /**
     * 每次造成伤害的时间间隔 / 周期
     */
    private long period;

    @Override
    public void init(SkillEffectResource skillEffectResource) {
        super.init(skillEffectResource);
        String effectValue = skillEffectResource.getEffectValue();
        String[] split = effectValue.split(StringUtil.XIA_HUA_XIAN);
        fixedHurt = Long.parseLong(split[0]);
        hurtProp = Long.parseLong(split[1]);
        period = Long.parseLong(split[2]);
    }

    @Override
    public void doActive(int mapId, CreatureUnit useUnit, CreatureUnit targetUnit, SkillLevelResource skillLevelResource, SkillResource skillResource) {

        CreatureAttributeContainer attributeContainer = useUnit.getAttributeContainer();
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        Attribute useAttackAttr = finalAttributes.get(AttributeType.MAGIC_ATTACK);
        double hurt = ComputeUtil.getHurtValue(useAttackAttr.getValue(), fixedHurt, hurtProp);
        /**
         * 持续性的伤害值会随目标的防御属性而改变
         */
        Map<AttributeType, Attribute> targetFinalAttributes = targetUnit.getAttributeContainer().getFinalAttributes();
        Attribute attribute = targetFinalAttributes.get(AttributeType.MAGIC_DEFENSE);
        double realHurt = ComputeUtil.getRealHurt(hurt, attribute.getValue());
        targetUnit.consumeHpAndDoDead((long) realHurt, useUnit);
        if (targetUnit.isDead()) {
            return;
        }
        /**
         * 定时扣血
         */
        SkillDurationDamageCommand command = SkillDurationDamageCommand.valueOf(mapId, useUnit, period,
                hurt, targetUnit, AttributeType.MAGIC_DEFENSE, TimeUtil.now() + duration, getEffectId());
        SpringContext.getSceneExecutorService().submit(command);
        targetUnit.putBuffCommand(SkillEffectAttributeId.getSkillAttributeId(effectId), command);
    }


    protected void doDestroy(CreatureUnit targetUnit) {
        ICommand command = targetUnit.getBuffCommandMap().get(SkillEffectAttributeId.getSkillAttributeId(effectId));
        command.cancel();
        targetUnit.removeBuffCommand(SkillEffectAttributeId.getSkillAttributeId(effectId));
    }
}

package com.game.role.skilleffect.command;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.SkillEffectAttributeId;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.executor.scene.impl.AbstractSceneRateCommand;
import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.role.skilleffect.model.DurationDamageEffect;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.util.ComputeUtil;
import com.game.util.TimeUtil;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 11:30
 */
public class SkillDurationDamageCommand extends AbstractSceneRateCommand {
    /**
     * 每次使用者应该造成的理论伤害
     */
    private double hurt;
    /**
     * 玩家的防御属性
     */
    private AttributeType defenceType;
    /**
     * 目标unit
     */
    private CreatureUnit targetUnit;
    /**
     * 使用技能的unit
     */
    private CreatureUnit useUnit;
    /**
     * 失效时间
     */
    private long invalidTime;
    /**
     * 效果id
     */
    private int effectId;

    public static SkillDurationDamageCommand valueOf(AbstractScene scene, CreatureUnit useUnit,
                                                     long period, double hurt, CreatureUnit targetUnit, AttributeType defenceType, long invalidTime, int effectId) {
        SkillDurationDamageCommand command = new SkillDurationDamageCommand(scene, period, period, useUnit.getAccountId());
        command.setHurt(hurt);
        command.setTargetUnit(targetUnit);
        command.setDefenceType(defenceType);
        command.setUseUnit(useUnit);
        command.setInvalidTime(invalidTime);
        command.setEffectId(effectId);
        return command;
    }

    public SkillDurationDamageCommand(AbstractScene scene, long delay, long period, String accountId) {
        super(scene, delay, period, accountId);
    }

    @Override
    public String getName() {
        return "SkillDurationDamageCommand";
    }

    @Override
    public void active() {
        if (TimeUtil.now() > invalidTime) {
            cancel();
            targetUnit.removeBuffCommand(SkillEffectAttributeId.getSkillAttributeId(effectId));
            return;
        }
        Map<AttributeType, Attribute> finalAttributes = targetUnit.getAttributeContainer().getFinalAttributes();
        Attribute attribute = finalAttributes.get(defenceType);
        double realHurt = ComputeUtil.getRealHurt(hurt, attribute.getValue());
        targetUnit.consumeHpAndDoDead((long) realHurt, useUnit);
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public long getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(long invalidTime) {
        this.invalidTime = invalidTime;
    }

    public CreatureUnit getUseUnit() {
        return useUnit;
    }

    public void setUseUnit(CreatureUnit useUnit) {
        this.useUnit = useUnit;
    }

    public AttributeType getDefenceType() {
        return defenceType;
    }

    public void setDefenceType(AttributeType defenceType) {
        this.defenceType = defenceType;
    }

    public double getHurt() {
        return hurt;
    }

    public void setHurt(double hurt) {
        this.hurt = hurt;
    }

    public CreatureUnit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(CreatureUnit targetUnit) {
        this.targetUnit = targetUnit;
    }
}

package com.game.role.skilleffect.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skill.packet.SM_UseSkill;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.context.SkillUseContext;
import com.game.role.skilleffect.context.SkillUseContextEnm;
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


    @Override
    public void doActive(SkillUseContext skillUseContext, CreatureUnit targetUnit) {

        CreatureUnit useUnit = skillUseContext.getParam(SkillUseContextEnm.SKILL_ATTACKER);
        /**
         * 计算真实扣血量
         */
        long realHurt = computeRealHurt(useUnit, targetUnit);
        /**
         * 做减血操作，如果死了就做死亡处理
         */
        targetUnit.consumeHpAndDoDead(realHurt, useUnit);
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

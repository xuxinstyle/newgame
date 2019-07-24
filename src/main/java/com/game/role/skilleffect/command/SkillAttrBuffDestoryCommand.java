package com.game.role.skilleffect.command;

import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.game.role.skill.packet.SM_SkillStatus;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.util.SendPacketUtil;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 10:16
 */
public class SkillAttrBuffDestoryCommand extends AbstractSceneDelayCommand {
    /**
     * 目标单元
     */
    private CreatureUnit targetUnit;
    /**
     * 属性id
     */
    private AttributeId attributeId;
    /**
     * 技能id
     */
    private int skillId;

    public static SkillAttrBuffDestoryCommand valueOf(AbstractScene scene, long delay, String accountId, CreatureUnit targetUnit, AttributeId attributeId, int skillId) {
        SkillAttrBuffDestoryCommand command = new SkillAttrBuffDestoryCommand(scene, delay, accountId);
        command.setAttributeId(attributeId);
        command.setTargetUnit(targetUnit);
        command.setSkillId(skillId);
        return command;
    }

    public SkillAttrBuffDestoryCommand(AbstractScene scene, long delay, String accountId) {
        super(scene, accountId, delay);
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    @Override
    public String getName() {
        return "SkillAttrBuffDestoryCommand";
    }

    @Override
    public void active() {
        CreatureAttributeContainer attributeContainer = targetUnit.getAttributeContainer();
        attributeContainer.removeAndCompteAttribtues(attributeId);
        targetUnit.removeBuffCommand(attributeId);
        SendPacketUtil.send(getAccountId(), SM_SkillStatus.valueOf(skillId, 3));
    }

    public CreatureUnit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(CreatureUnit targetUnit) {
        this.targetUnit = targetUnit;
    }

    public AttributeId getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(AttributeId attributeId) {
        this.attributeId = attributeId;
    }
}

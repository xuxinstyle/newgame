package com.game.role.skilleffect.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.SkillEffectAttributeId;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.role.skill.packet.SM_AddAttributeEffect;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.command.SkillBuffDelayCommand;
import com.game.role.skilleffect.constant.SkillEffectType;
import com.game.role.skilleffect.resource.SkillEffectResource;
import com.game.scence.fight.model.CreatureUnit;
import com.game.util.AttributeAnalyzeUtil;
import com.game.util.SendPacketUtil;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/12 9:30
 */
public class AddMyselfAttributeEffect extends AbstractSkillEffect {
    /**
     * 增加的属性列表
     */
    private List<Attribute> attributeList;

    @Override
    public void init(SkillEffectResource skillEffectResource) {
        super.init(skillEffectResource);
        String effectValue = skillEffectResource.getEffectValue();
        attributeList = AttributeAnalyzeUtil.analyzeAttr(effectValue);
        duration = skillEffectResource.getDuration();
    }

    @Override
    public void doActive(int mapId, CreatureUnit useUnit, CreatureUnit targetUnit, SkillLevelResource skillLevelResource, SkillResource skillResource) {
        useUnit.setCurrMp(useUnit.getCurrMp() - skillLevelResource.getConsumeMp());
        CreatureAttributeContainer attributeContainer = targetUnit.getAttributeContainer();
        /**
         * 将效果加到unit身上
         */
        attributeContainer.putAndComputeAttributes(SkillEffectAttributeId.
                getSkillAttributeId(SkillEffectType.ADD_ATTRIBUTE.getId()), attributeList);
        SendPacketUtil.send(useUnit.getAccountId(), SM_AddAttributeEffect.valueOf(attributeList));

        /**
         * 抛出command 定时移除效果
         */
        SkillBuffDelayCommand command = SkillBuffDelayCommand.valueOf(mapId, duration, useUnit.getAccountId(), targetUnit,
                SkillEffectAttributeId.getSkillAttributeId(SkillEffectType.ADD_ATTRIBUTE.getId()), skillResource.getId());
        SpringContext.getSceneExecutorService().submit(command);
        targetUnit.putBuffCommand(SkillEffectAttributeId.getSkillAttributeId(SkillEffectType.ADD_ATTRIBUTE.getId()), command);

    }

    @Override
    protected void doDestroy(CreatureUnit targetUnit) {
        super.doDestroy(targetUnit);

    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}

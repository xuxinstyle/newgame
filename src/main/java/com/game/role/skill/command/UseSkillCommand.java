package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 20:33
 */
public class UseSkillCommand extends AbstractSceneCommand {
    /**
     * 使用者
     */
    private CreatureUnit useUnit;

    /**
     * 目标
     */
    private CreatureUnit targetUnit;

    /**
     * 快捷技能栏id
     */
    private int skillBarId;

    public UseSkillCommand(AbstractScene scene, String accountId) {
        super(scene, accountId);
    }


    public static UseSkillCommand valueOf(String accountId, AbstractScene scene, CreatureUnit targetUnit, CreatureUnit useUnit, int skillBarId) {
        UseSkillCommand command = new UseSkillCommand(scene, accountId);
        command.setSkillBarId(skillBarId);
        command.setUseUnit(useUnit);
        command.setTargetUnit(targetUnit);
        return command;
    }

    @Override
    public String getName() {
        return "UseSkillCommand";
    }

    @Override
    public void active() {

        SpringContext.getSkillService().doUseSkill(getScene(), useUnit, targetUnit, skillBarId);
    }


    public int getSkillBarId() {
        return skillBarId;
    }

    public void setSkillBarId(int skillBarId) {
        this.skillBarId = skillBarId;
    }

    public CreatureUnit getUseUnit() {
        return useUnit;
    }

    public void setUseUnit(CreatureUnit useUnit) {
        this.useUnit = useUnit;
    }

    public CreatureUnit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(CreatureUnit targetUnit) {
        this.targetUnit = targetUnit;
    }
}

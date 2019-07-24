package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 22:59
 */
public class SkillCdCommand extends AbstractSceneDelayCommand {

    /**
     * 技能id
     */
    private int skillId;

    private CreatureUnit creatureUnit;

    public SkillCdCommand(int mapId, long delay, String accountId) {
        super(mapId, 0, delay, accountId);
    }

    public static SkillCdCommand valueOf(int mapId, int skillId, long delay, String accountId, CreatureUnit creatureUnit) {
        SkillCdCommand command = new SkillCdCommand(mapId, delay, accountId);
        command.setSkillId(skillId);
        command.setCreatureUnit(creatureUnit);
        return command;
    }

    @Override
    public String getName() {
        return "SkillCdCommand";
    }

    @Override
    public void active() {
        creatureUnit.clearSkillCd(skillId);
    }

    public CreatureUnit getCreatureUnit() {
        return creatureUnit;
    }

    public void setCreatureUnit(CreatureUnit creatureUnit) {
        this.creatureUnit = creatureUnit;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }
}

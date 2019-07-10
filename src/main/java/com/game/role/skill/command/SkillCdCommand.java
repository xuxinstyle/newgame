package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 22:59
 */
public class SkillCdCommand extends AbstractSceneDelayCommand {

    /**
     * 技能id
     */
    private int skillId;

    public SkillCdCommand(int mapId, long delay, String accountId) {
        super(mapId, delay, accountId);
    }

    @Override
    public String getName() {
        return "SkillCdCommand";
    }

    @Override
    public void active() {
        
    }
}

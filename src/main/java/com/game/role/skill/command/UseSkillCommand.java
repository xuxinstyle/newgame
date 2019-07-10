package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 20:33
 */
public class UseSkillCommand extends AbstractSceneCommand {
    /**
     * 技能id
     */
    private int skillBarId;
    /**
     * 目标id
     */
    private long targetId;
    /**
     * 使用者id
     */
    private long useId;

    public UseSkillCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);

    }
    public static UseSkillCommand valueOf(int mapId, int sceneId, String accountId,int skillBarId,long targetId,long useId){
        UseSkillCommand command = new UseSkillCommand(mapId,sceneId,accountId);
        command.setSkillBarId(skillBarId);
        command.setTargetId(targetId);
        command.setUseId(useId);
        return command;
    }
    @Override
    public String getName() {
        return "UseSkillCommand";
    }

    @Override
    public void active() {
        SpringContext.getSkillService().doUseSkill(getAccountId(),getMapId(),getSceneId(), skillBarId,targetId,useId);
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public long getUseId() {
        return useId;
    }

    public void setUseId(long useId) {
        this.useId = useId;
    }

    public int getSkillBarId() {
        return skillBarId;
    }

    public void setSkillBarId(int skillBarId) {
        this.skillBarId = skillBarId;
    }
}

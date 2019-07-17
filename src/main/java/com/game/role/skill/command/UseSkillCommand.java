package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 20:33
 */
public class UseSkillCommand extends AbstractSceneCommand {
    /**
     * 使用者
     */
    private long useId;
    /**
     * 使用者类型
     */
    private ObjectType useType;
    /**
     * 目标
     */
    private long targetId;
    /**
     * 目标类型
     */
    private ObjectType targetType;

    /**
     * 快捷技能栏id
     */
    private int skillBarId;

    public UseSkillCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);

    }

    public static UseSkillCommand valueOf(String accountId, int mapId, long targetId, long useId, ObjectType useType, int skillBarId, ObjectType targetType) {
        UseSkillCommand command = new UseSkillCommand(mapId, 0, accountId);
        command.setUseId(useId);
        command.setTargetId(targetId);
        command.setSkillBarId(skillBarId);
        command.setTargetType(targetType);
        command.setSkillBarId(skillBarId);
        command.setUseType(useType);
        return command;

    }

    @Override
    public String getName() {
        return "UseSkillCommand";
    }

    @Override
    public void active() {

        SpringContext.getSkillService().doUseSkill(getAccountId(), getMapId(), useId, useType, targetId, targetType, skillBarId);
    }

    public ObjectType getUseType() {
        return useType;
    }

    public void setUseType(ObjectType useType) {
        this.useType = useType;
    }

    public long getUseId() {
        return useId;
    }

    public void setUseId(long useId) {
        this.useId = useId;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public ObjectType getTargetType() {
        return targetType;
    }

    public void setTargetType(ObjectType targetType) {
        this.targetType = targetType;
    }

    public int getSkillBarId() {
        return skillBarId;
    }

    public void setSkillBarId(int skillBarId) {
        this.skillBarId = skillBarId;
    }
}

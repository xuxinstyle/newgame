package com.game.role.skilleffect.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skilleffect.constant.BuffType;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 16:16
 */
public class ShowEffectCommand extends AbstractSceneCommand {
    /**
     * 目标类型
     */
    private ObjectType objectType;
    /**
     * 目标id
     */
    private long targetId;

    public static ShowEffectCommand valueOf(String accountId, int mapId, ObjectType objectType, long targetId) {
        ShowEffectCommand command = new ShowEffectCommand(mapId, 0, accountId);
        command.setObjectType(objectType);
        command.setTargetId(targetId);
        return command;
    }

    public ShowEffectCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    @Override
    public String getName() {
        return "ShowEffectCommand";
    }

    @Override
    public void active() {
        SpringContext.getSkillEffectService().showUnitEffect(getAccountId(), getMapId(), getObjectType(), getTargetId());
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
}

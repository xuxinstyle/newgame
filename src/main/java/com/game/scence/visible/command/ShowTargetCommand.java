package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.field.packet.SM_ShowMonsterInfo;
import com.game.scence.fight.model.CreatureUnit;
import com.game.util.SendPacketUtil;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 23:30
 */
public class ShowTargetCommand extends AbstractSceneCommand {

    private ObjectType objectType;

    private long objectId;

    public ShowTargetCommand(int mapId, int sceneId, String accountId) {

        super(mapId, sceneId, accountId);
    }

    public ShowTargetCommand(AbstractScene scene, String accountId) {

        super(scene, accountId);
    }

    public static ShowTargetCommand valueOf(AbstractScene scene, String accountId, ObjectType objectType, long objectId) {
        ShowTargetCommand command = new ShowTargetCommand(scene, accountId);
        command.setObjectId(objectId);
        command.setObjectType(objectType);
        return command;
    }

    public static ShowTargetCommand valueOf(int mapId, int sceneId, String accountId, ObjectType objectType, long objectId) {
        ShowTargetCommand command = new ShowTargetCommand(mapId, sceneId, accountId);
        command.setObjectId(objectId);
        command.setObjectType(objectType);
        return command;
    }

    @Override
    public String getName() {
        return "ShowTargetCommand";
    }

    @Override
    public void active() {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId(), getAccountId());
        if (scene == null) {
            return;
        }
        CreatureUnit unit = scene.getUnit(objectType, objectId);
        SendPacketUtil.send(getAccountId(), SM_ShowMonsterInfo.valueOf(unit));
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }
}

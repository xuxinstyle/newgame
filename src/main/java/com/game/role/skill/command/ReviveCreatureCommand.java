package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.game.base.gameobject.model.Creature;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 17:39
 */
public class ReviveCreatureCommand extends AbstractSceneDelayCommand {

    /**
     * 需要复活的生物
     */
    private CreatureUnit creatureUnit;

    public ReviveCreatureCommand(AbstractScene scene, long delay, String accountId) {
        super(scene, accountId, delay);
    }

    public static ReviveCreatureCommand valueOf(AbstractScene scene, long delay, String accountId, CreatureUnit creatureUnit) {
        ReviveCreatureCommand command = new ReviveCreatureCommand(scene, delay, accountId);
        command.setCreatureUnit(creatureUnit);
        return command;
    }
    @Override
    public String getName() {
        return "ReviveCreatureCommand";
    }

    @Override
    public void active() {
        creatureUnit.doRevive();
    }

    public CreatureUnit getCreatureUnit() {
        return creatureUnit;
    }

    public void setCreatureUnit(CreatureUnit creatureUnit) {
        this.creatureUnit = creatureUnit;
    }
}

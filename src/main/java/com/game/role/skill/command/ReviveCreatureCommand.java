package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.game.base.gameobject.model.Creature;
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

    public ReviveCreatureCommand(int mapId, long delay, String accountId) {
        super(mapId, delay, accountId);
    }

    public static ReviveCreatureCommand valueOf(int mapId, long delay, String accountId,CreatureUnit creatureUnit){
        ReviveCreatureCommand command = new ReviveCreatureCommand(mapId,delay,accountId);
        command.setCreatureUnit(creatureUnit);
        return command;
    }
    @Override
    public String getName() {
        return "ReviveCreatureCommand";
    }

    @Override
    public void active() {
        SpringContext.getSkillService().doCreatureRevive(getMapId(),creatureUnit);
    }

    public CreatureUnit getCreatureUnit() {
        return creatureUnit;
    }

    public void setCreatureUnit(CreatureUnit creatureUnit) {
        this.creatureUnit = creatureUnit;
    }
}

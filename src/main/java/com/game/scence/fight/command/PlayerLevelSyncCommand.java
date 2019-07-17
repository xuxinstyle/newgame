package com.game.scence.fight.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.PlayerUnit;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 15:07
 */
public class PlayerLevelSyncCommand extends AbstractSceneCommand {

    private Player player;

    public static PlayerLevelSyncCommand valueOf(int mapId, String accountId, Player player) {
        PlayerLevelSyncCommand command = new PlayerLevelSyncCommand(mapId, 0, accountId);
        command.setPlayer(player);
        return command;
    }

    public PlayerLevelSyncCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    @Override
    public String getName() {
        return "PlayerLevelSyncCommand";
    }

    @Override
    public void active() {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId());
        Map<Long, CreatureUnit> creatureUnitMap = scene.getCreatureUnitMap();
        CreatureUnit creatureUnit = creatureUnitMap.get(player.getObjectId());
        if (creatureUnit == null) {
            return;
        }
        PlayerUnit playerUnit = PlayerUnit.valueOf(player);
        creatureUnitMap.put(player.getObjectId(), playerUnit);

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

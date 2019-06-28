package com.game.scence.command;

import com.game.SpringContext;
import com.game.common.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;
import com.game.scence.model.PlayerPosition;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:37
 */
public class MoveCommand extends AbstractSceneCommand {

    private Player player;

    private PlayerPosition currPosition;

    private PlayerPosition targetPosition;

    public MoveCommand(int mapId, Player player, PlayerPosition currPosition, PlayerPosition targetPosition){
        super(mapId);
        this.player = player;
        this.currPosition = currPosition;
        this.targetPosition = targetPosition;
    }
    public static MoveCommand valueOf(int mapId,Player player,PlayerPosition currPosition, PlayerPosition targetPosition ){
        MoveCommand command = new MoveCommand(mapId, player, currPosition, targetPosition);
        return command;
    }

    @Override
    public String getName() {
        return "MoveCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doMove(player.getAccountId(),targetPosition,getMapId());
    }
}

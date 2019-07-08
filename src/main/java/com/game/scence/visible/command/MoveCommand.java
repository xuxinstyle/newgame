package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;
import com.game.scence.visible.model.Position;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:37
 */
public class MoveCommand extends AbstractSceneCommand {

    private Player player;

    private Position currPosition;

    private Position targetPosition;

    public MoveCommand(int mapId, Player player, Position currPosition, Position targetPosition){
        super(mapId,player.getCurrSceneId(),player.getAccountId());
        this.player = player;
        this.currPosition = currPosition;
        this.targetPosition = targetPosition;
    }
    public static MoveCommand valueOf(int mapId, Player player, Position currPosition, Position targetPosition ){
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(Position currPosition) {
        this.currPosition = currPosition;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
    }
}

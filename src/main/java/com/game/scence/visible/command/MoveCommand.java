package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;
import com.game.scence.visible.model.Position;
import io.netty.util.collection.CharObjectMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:37
 */
public class MoveCommand extends AbstractSceneCommand {

    private Player player;

    private Position currPosition;

    private Position targetPosition;

    public MoveCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    public static MoveCommand valueOf(Player player, Position targetPosition) {
        MoveCommand command = new MoveCommand(player.getCurrMapId(), 0, player.getAccountId());
        command.setTargetPosition(targetPosition);
        command.setCurrPosition(player.getPosition());
        command.setPlayer(player);
        return command;
    }

    @Override
    public String getName() {
        return "MoveCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doMove(player, targetPosition, getMapId());
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

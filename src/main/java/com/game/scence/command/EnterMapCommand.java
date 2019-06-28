package com.game.scence.command;

import com.game.SpringContext;
import com.game.common.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:47
 */
public class EnterMapCommand extends AbstractSceneCommand {
    private Player player;

    private int targetMapId;

    public EnterMapCommand(int currMapId, int targetMapId, Player player) {
        super(currMapId);
        this.targetMapId = targetMapId;
        this.player = player;
    }
    public static EnterMapCommand valueOf(Player player, int currMapId,int targetMapId){
        EnterMapCommand command = new EnterMapCommand(currMapId, targetMapId, player);
        return command;
    }
    @Override
    public String getName() {
        return "EnterMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doEnterMap(player.getAccountId(),targetMapId);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTargetMapId() {
        return targetMapId;
    }

    public void setTargetMapId(int targetMapId) {
        this.targetMapId = targetMapId;
    }
}

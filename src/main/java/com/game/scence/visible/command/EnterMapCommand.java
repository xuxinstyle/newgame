package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/27 11:47
 */
public class EnterMapCommand extends AbstractSceneCommand {
    private Player player;

    private int targetMapId;

    public EnterMapCommand(int targetMapId, Player player) {
        super(player.getCurrMapId(),player.getCurrSceneId(),player.getAccountId());
        this.targetMapId = targetMapId;
        this.player = player;
    }

    public static EnterMapCommand valueOf(Player player, int targetMapId) {
        EnterMapCommand command = new EnterMapCommand(targetMapId, player);
        return command;
    }
    @Override
    public String getName() {
        return "EnterMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doEnterMap(player, targetMapId);
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

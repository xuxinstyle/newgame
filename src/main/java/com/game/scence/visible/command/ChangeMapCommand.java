package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 10:19
 */
public class ChangeMapCommand extends AbstractSceneCommand {

    private Player player;
    /**
     * 目标地图id
     */
    private AbstractScene targetScene;
    /**
     * 是否第一次进入
     */
    private boolean clientRequest;

    public ChangeMapCommand(Player player, AbstractScene scene) {
        super(scene, player.getAccountId());
        this.player = player;

    }

    public static ChangeMapCommand valueOf(Player player, AbstractScene scene, boolean clientRequest) {
        ChangeMapCommand command = new ChangeMapCommand(player, scene);
        command.setTargetScene(scene);
        command.setClientRequest(clientRequest);
        return command;
    }

    public boolean isClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(boolean clientRequest) {
        this.clientRequest = clientRequest;
    }

    public AbstractScene getTargetScene() {
        return targetScene;
    }

    public void setTargetScene(AbstractScene targetScene) {
        this.targetScene = targetScene;
    }

    @Override
    public String getName() {
        return "ChangeMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doChangeMap(player, targetScene, clientRequest);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

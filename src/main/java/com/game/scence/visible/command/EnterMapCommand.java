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
    /**
     * 是否是登陆时的进入请求
     */
    private boolean isLoginEnterRequest;

    public EnterMapCommand(int targetMapId, int sceneId, Player player) {
        super(targetMapId, sceneId, player.getAccountId());
        this.player = player;
    }

    public static EnterMapCommand valueOf(Player player, int sceneId, int targetMapId, boolean isLoginEnterRequest) {
        EnterMapCommand command = new EnterMapCommand(targetMapId, sceneId, player);
        command.setLoginEnterRequest(isLoginEnterRequest);
        return command;
    }
    @Override
    public String getName() {
        return "EnterMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doEnterMap(player, getMapId(), isLoginEnterRequest);
    }

    public boolean isLoginEnterRequest() {
        return isLoginEnterRequest;
    }

    public void setLoginEnterRequest(boolean loginEnterRequest) {
        this.isLoginEnterRequest = loginEnterRequest;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}

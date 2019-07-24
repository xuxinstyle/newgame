package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/5 14:25
 */
public class LeaveMapCommand extends AbstractSceneCommand {

    private boolean clientRequest;

    public LeaveMapCommand(int mapId, int sceneId, String accountId, boolean clientRequest) {
        super(mapId, sceneId, accountId);
        this.clientRequest = clientRequest;

    }

    public boolean isClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(boolean clientRequest) {
        this.clientRequest = clientRequest;
    }

    @Override
    public String getName() {
        return "LeaveMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().leaveMap(getAccountId(), clientRequest);
    }
}

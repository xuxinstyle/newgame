package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/5 14:25
 */
public class LeaveMapCommand extends AbstractSceneCommand {

    public LeaveMapCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    @Override
    public String getName() {
        return "LeaveMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().leaveMap(getAccountId());
    }
}

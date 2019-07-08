package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 9:34
 */
public class RemoveScenceInfoCommand extends AbstractSceneCommand {

    private String accountId;

    public RemoveScenceInfoCommand(int mapId,String accountId ) {
        super(mapId,0,accountId);
        this.accountId = accountId;
    }
    public static RemoveScenceInfoCommand valueOf(int mapId ,String accountId){
        RemoveScenceInfoCommand command = new RemoveScenceInfoCommand(mapId, accountId);
        return command;
    }
    @Override
    public String getName() {
        return "RemoveScenceInfoCommand";
    }

    @Override
    public void active() {
        // TODO:
    }
}

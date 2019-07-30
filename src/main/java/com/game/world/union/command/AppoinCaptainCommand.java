package com.game.world.union.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 14:34
 */
public class AppoinCaptainCommand extends AbstractAccountCommand {

    private String handleAccountId;

    private String unionId;

    public static AppoinCaptainCommand valueOf(String handleAccountId, String unionId, String targetAccountId) {
        AppoinCaptainCommand command = new AppoinCaptainCommand(targetAccountId);
        command.setHandleAccountId(handleAccountId);
        command.setUnionId(unionId);
        return command;
    }

    public AppoinCaptainCommand(String accountId) {
        super(accountId);
    }

    @Override
    public String getName() {
        return "AppoinCaptainCommand";
    }

    @Override
    public void active() {
        SpringContext.getUnionService().doAppoinCaptain(handleAccountId, unionId, getAccountId());
    }

    public String getHandleAccountId() {
        return handleAccountId;
    }

    public void setHandleAccountId(String handleAccountId) {
        this.handleAccountId = handleAccountId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}

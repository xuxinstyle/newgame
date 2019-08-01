package com.game.world.union.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;

/**
 * @Author：xuxin
 * @Date: 2019/8/1 16:19
 */
public class KickCommand extends AbstractAccountCommand {
    /**
     * 操作者id
     */
    private String handleAccountId;
    /**
     * 目标工会id
     */
    private String unionId;

    public static KickCommand valueOf(String handleAccountId, String unionId, String targetAccountId) {
        KickCommand command = new KickCommand(targetAccountId);
        command.setHandleAccountId(handleAccountId);
        command.setUnionId(unionId);
        return command;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public KickCommand(String accountId) {
        super(accountId);
    }

    public String getHandleAccountId() {
        return handleAccountId;
    }

    public void setHandleAccountId(String handleAccountId) {
        this.handleAccountId = handleAccountId;
    }


    @Override
    public String getName() {
        return "KickCommand";
    }

    @Override
    public void active() {
        SpringContext.getUnionService().doKickOther(handleAccountId, unionId, getAccountId());
    }
}

package com.game.world.union.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 9:13
 */
public class KickCommand extends AbstractAccountCommand {
    /**
     * 操作者id
     */
    private String handleAccountId;
    /**
     * 工会id
     */
    private String unionId;

    public static KickCommand valueOf(String accountId, String unionId, String targetAccountId) {
        KickCommand command = new KickCommand(targetAccountId);
        command.setHandleAccountId(accountId);
        command.setUnionId(unionId);
        return command;
    }

    public KickCommand(String accountId) {
        super(accountId);
    }

    @Override
    public String getName() {
        return "KickCommand";
    }

    @Override
    public void active() {
        SpringContext.getUnionService().doKickOther(handleAccountId, unionId, getAccountId());
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

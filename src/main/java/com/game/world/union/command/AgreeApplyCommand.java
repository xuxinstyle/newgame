package com.game.world.union.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 14:47
 */
public class AgreeApplyCommand extends AbstractAccountCommand {
    /**
     * 工会id
     */
    private String unionId;
    /**
     * 操作者
     */
    private String handleAccountId;

    public static AgreeApplyCommand valueOf(String accountId, String targetAccountId, String unionId) {
        AgreeApplyCommand command = new AgreeApplyCommand(targetAccountId);
        command.setUnionId(unionId);
        command.setHandleAccountId(accountId);
        return command;
    }

    public AgreeApplyCommand(String accountId) {
        super(accountId);
    }

    @Override
    public String getName() {
        return "AgreeApplyCommand";
    }

    @Override
    public void active() {
        SpringContext.getUnionService().doAgreeApply(handleAccountId, unionId, getAccountId());
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getHandleAccountId() {
        return handleAccountId;
    }

    public void setHandleAccountId(String handleAccountId) {
        this.handleAccountId = handleAccountId;
    }
}

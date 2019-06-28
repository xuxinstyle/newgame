package com.game.common.executor.account.impl;

import com.game.common.executor.AbstractCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:25
 */
public abstract class AbstractAccountCommand extends AbstractCommand {
    private String accountId;
    public AbstractAccountCommand(String accountId){
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public Object getKey() {
        return accountId;
    }

    @Override
    public int modIndex(int poolsize) {
        return Math.abs(accountId.hashCode() % poolsize);
    }
}

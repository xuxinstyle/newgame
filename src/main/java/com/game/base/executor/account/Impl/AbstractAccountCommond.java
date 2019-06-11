package com.game.base.executor.account.Impl;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:25
 */
public abstract class AbstractAccountCommond extends AbstractCommand{
    private String accountId;
    public AbstractAccountCommond(String accountId){
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

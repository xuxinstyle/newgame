package com.game.base.account.model;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 21:04
 */
public class Account {
    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 账号昵称
     */
    private String accountName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 账号数据
     */
    private byte[] accountData;

    private transient AccountInfo accountInfo;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

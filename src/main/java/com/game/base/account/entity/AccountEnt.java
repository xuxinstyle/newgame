package com.game.base.account.entity;

import com.game.base.account.model.AccountInfo;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 21:04
 */
public class AccountEnt {
    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 密码
     */
    private String passward;
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

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
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

    public byte[] getAccountData() {
        return accountData;
    }

    public void setAccountData(byte[] accountData) {
        this.accountData = accountData;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }
}

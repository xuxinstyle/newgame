package com.game.role.account.entity;

import com.db.AbstractEntity;
import com.game.role.account.model.AccountInfo;
import com.socket.Utils.ProtoStuffUtil;

import javax.persistence.Transient;
import java.util.Arrays;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 21:04
 */
public class AccountEnt extends AbstractEntity<String> {
    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 密码
     */
    private String passward;

    private byte[] accountData;
    @Transient
    private AccountInfo accountInfo;

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    @Override
    public String getId() {
        return accountId;
    }


    @Override
    public void doSerialize() {
        this.accountData = ProtoStuffUtil.serializer(accountInfo);
    }

    @Override
    public void doDeserialize() {
        this.accountInfo = ProtoStuffUtil.deserializer(this.accountData, AccountInfo.class);
    }

    @Override
    public String toString() {
        return "AccountEnt{" +
                "accountId='" + accountId + '\'' +
                ", passward='" + passward + '\'' +
                ", accountData=" + Arrays.toString(accountData) +
                ", accountInfo=" + accountInfo +
                '}';
    }
}

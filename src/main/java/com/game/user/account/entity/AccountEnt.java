package com.game.user.account.entity;

import com.db.AbstractEntity;
import com.game.user.account.model.AccountInfo;
import com.socket.Utils.ProtoStuffUtil;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Arrays;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 21:04
 */
@Entity(name = "account")
/*@Table(appliesTo = "account", comment = "账号信息")*/
public class AccountEnt extends AbstractEntity<String> {
    /**
     * 账号Id
     */
    @Id
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '账号Id'",nullable = false)
    private String accountId;
    /**
     * 密码
     */
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '密码'",nullable = false)
    private String passward;

    @Lob
    @Column(columnDefinition = "blob comment '账号基本数据'",nullable = false)
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

package com.game.user.account.model;

import com.game.util.TimeUtil;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 放账号的信息，如属性等
 * @Author：xuxin
 * @Date: 2019/5/18 16:26
 */

public class AccountInfo {

    /**
     * 账号昵称
     */
    private String accountName;
    /**
     * 建号时间
     */
    private long createTime;
    /**
     * 上次登录时间
     */
    private long lastLoginTime;
    /**
     * 上次登出时间
     */
    private long lastLogoutTime;
    /**
     * 该账号下的PlayerId
     */
    private long playerId;
    /**
     * 是否是gm玩家
     */
    private boolean isGm;
    /**
     * 改变地图的状态
     */
    @JsonIgnore
    private AtomicBoolean isChangeMap = new AtomicBoolean(false);

    public static AccountInfo valueOf(String accountName){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountName(accountName);
        accountInfo.setCreateTime(TimeUtil.now());
        accountInfo.setLastLoginTime(0L);

        accountInfo.setLastLogoutTime(0L);
        accountInfo.setPlayerId(0L);
        return accountInfo;
    }

    public AtomicBoolean getIsChangeMap() {

        return isChangeMap;
    }

    public void setIsChangeMap(AtomicBoolean isChangeMap) {
        this.isChangeMap = isChangeMap;
    }

    public boolean isGm() {
        return isGm;
    }

    public void setGm(boolean gm) {
        isGm = gm;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(long lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountName='" + accountName + '\'' +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLogoutTime=" + lastLogoutTime +
                ", playerId=" + playerId +
                ", isGm=" + isGm +
                '}';
    }
}

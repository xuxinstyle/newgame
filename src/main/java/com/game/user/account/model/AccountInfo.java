package com.game.user.account.model;

import com.game.scence.constant.SceneType;
import com.game.util.TimeUtil;

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
     * 上次登出时所在场景地图 默认新手村
     */
    private SceneType lastLogoutMapType;
    /**
     * 玩家当前所在的地图
     */
    private SceneType currentMapType;
    /**
     * 该账号下的PlayerId
     */
    private long playerId;

    public static AccountInfo valueOf(String accountName){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountName(accountName);
        accountInfo.setCreateTime(TimeUtil.now());
        accountInfo.setLastLoginTime(0L);
        accountInfo.setCurrentMapType(SceneType.NoviceVillage);
        accountInfo.setLastLogoutMapType(SceneType.NoviceVillage);
        accountInfo.setLastLogoutTime(0L);
        accountInfo.setPlayerId(0L);
        return accountInfo;
    }

    public SceneType getCurrentMapType() {
        return currentMapType;
    }

    public void setCurrentMapType(SceneType currentMapType) {
        this.currentMapType = currentMapType;
    }

    public SceneType getLastLogoutMapType() {
        return lastLogoutMapType;
    }

    public void setLastLogoutMapType(SceneType lastLogoutMapType) {
        this.lastLogoutMapType = lastLogoutMapType;
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
                ", lastLogoutMapType=" + lastLogoutMapType +
                ", currentMapType=" + currentMapType +
                ", playerId=" + playerId +
                '}';
    }
}

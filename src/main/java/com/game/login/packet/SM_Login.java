package com.game.login.packet;

import org.msgpack.annotation.Message;

/**
 * @Author：xuxin
 * @Date: 2019/5/18 15:55
 * @Id 3
 */
@Message
public class SM_Login {
    /**登录状态 1 成功， 0 失败：密码错误 -1  有玩家在线 */
    private int status;
    /**
     * 玩家账号Id
     */
    private String accountId;
    /**
     * 玩家角色id
     */
    private long playerId;
    /**
     * 玩家上次登出时所在的场景Id
     */
    private int lastScenceId;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getLastScenceId() {
        return lastScenceId;
    }

    public void setLastScenceId(int lastScenceId) {
        this.lastScenceId = lastScenceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

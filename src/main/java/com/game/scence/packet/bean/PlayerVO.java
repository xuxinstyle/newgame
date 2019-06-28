package com.game.scence.packet.bean;

import com.game.scence.model.PlayerPosition;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 14:05
 */
public class PlayerVO {
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 角色名
     */
    private String playerName;
    /**
     * 职业
     */
    private int jobType;
    /**
     * 等级
     */
    private int level;
    /**
     * 玩家位置
     */
    private PlayerPosition position;

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}

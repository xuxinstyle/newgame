package com.game.scence.packet.bean;

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
     * x
     */
    private int x;
    private int y;

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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

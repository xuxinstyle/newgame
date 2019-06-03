package com.game.base.account.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 14:04
 * @id 7
 */
public class CM_CreatePlayer {
    /**
     * 玩家昵称
     */
    private String nickName;
    /**
     * 账号Id
     */
    private String accountId;

    /**
     * 职业
     */
    private String career;

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

package com.game.scence.packet;

import com.game.scence.model.PlayerPosition;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 10:11
 */
public class SM_ShowAccountInfo {

    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 账号昵称
     */
    private String nickName;
    /**
     * 职业
     */
    private String career;
    /**
     * 等级
     */
    private int level;

    /**
     * 角色名
     */
    private String playerName;

    /**
     * 位置
     *
     */
    private PlayerPosition position;

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

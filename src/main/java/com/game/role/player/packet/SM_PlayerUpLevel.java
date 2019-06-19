package com.game.role.player.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 17:43
 */
public class SM_PlayerUpLevel {
    /**
     * 升级前等级
     */
    private int level;
    /**
     * 升级后等级
     */
    private int upLevel;
    /**
     *
     * 角色名
     */
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpLevel() {
        return upLevel;
    }

    public void setUpLevel(int upLevel) {
        this.upLevel = upLevel;
    }
}

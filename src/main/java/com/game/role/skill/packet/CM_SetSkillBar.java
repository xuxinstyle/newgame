package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 11:31
 */
public class CM_SetSkillBar {
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 技能栏的设置顺序
     */
    private String setStr;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getSetStr() {
        return setStr;
    }

    public void setSetStr(String setStr) {
        this.setStr = setStr;
    }
}

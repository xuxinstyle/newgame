package com.game.role.skill.packet;

/**
 * 技能栏
 * @Author：xuxin
 * @Date: 2019/7/8 22:48
 */
public class CM_ShowSkillInfo {
    /**
     * 角色id
     */
    private long playerId;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}

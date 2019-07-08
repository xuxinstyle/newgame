package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 21:54
 */
public class CM_UpgradeSkill {
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 技能id
     */
    private int skillId;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }
}

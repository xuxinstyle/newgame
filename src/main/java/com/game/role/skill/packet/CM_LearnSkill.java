package com.game.role.skill.packet;

/**
 * 请求学习技能
 * @Author：xuxin
 * @Date: 2019/7/8 19:47
 */
public class CM_LearnSkill {
    /**
     * 技能唯一id
     */
    private int skillId; /**
     * 角色id
     */
    private long playerId;

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

package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 11:14
 */
public class SM_SkillStatus {
    /**
     * 技能id
     */
    private int skillId;
    /**
     * cd状态 1 非cd状态  2 cd状态 3 技能失效
     */
    private int status;

    public static SM_SkillStatus valueOf(int skillId, int status) {
        SM_SkillStatus sm = new SM_SkillStatus();
        sm.setSkillId(skillId);
        sm.setStatus(status);
        return sm;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

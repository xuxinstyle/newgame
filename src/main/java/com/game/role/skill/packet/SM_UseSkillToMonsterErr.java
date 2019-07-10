package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 11:52
 */
public class SM_UseSkillToMonsterErr {
    /**
     * 2 地图中没有目标 3 使用者没有学习技能 4 距离太远 5 该地图不能释放技能 6 你已经死了 7 技能cd
     */
    private int status;

    public static SM_UseSkillToMonsterErr valueOf(int status){
        SM_UseSkillToMonsterErr sm = new SM_UseSkillToMonsterErr();
        sm.setStatus(status);
        return sm;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

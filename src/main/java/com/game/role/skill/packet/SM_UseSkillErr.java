package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 11:52
 */
public class SM_UseSkillErr {
    /**
     * 1 目标以死亡 2 地图中没有目标 3 技能栏没有技能 4 距离太远 5 该地图不能释放技能 6 你已经死了 7 技能cd 8 蓝不足 9  技能栏没有技能
     */
    private int status;

    public static SM_UseSkillErr valueOf(int status) {
        SM_UseSkillErr sm = new SM_UseSkillErr();
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

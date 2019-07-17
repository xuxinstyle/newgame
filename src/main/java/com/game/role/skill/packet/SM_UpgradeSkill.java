package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 21:56
 */
public class SM_UpgradeSkill {
    /**
     * 1 成功 2 失败达到最大技能等级 3
     */
    private int status;
    public static SM_UpgradeSkill valueOf(int status){
        SM_UpgradeSkill sm = new SM_UpgradeSkill();
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

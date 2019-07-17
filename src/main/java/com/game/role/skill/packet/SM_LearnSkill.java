package com.game.role.skill.packet;

/**
 * 返回玩家学习了技能后的显示信息
 * @Author：xuxin
 * @Date: 2019/7/8 19:58
 */
public class SM_LearnSkill {
    /**
     * 1 成功 2 失败 3 你已经学了该技能 无法重复学习 4 没有该技能
     */
    private int status;

    public static SM_LearnSkill valueOf(int status){
        SM_LearnSkill sm = new SM_LearnSkill();
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

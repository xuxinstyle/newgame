package com.game.role.skill.constant;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 12:23
 */
public enum SkillType {
    /**
     * 主动技能
     */
    ACTIVE_SKILL(1),
    /**
     * 被动技能
     */
    PASSIVE_SKILL(2),
    ;
    int id;
    SkillType(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

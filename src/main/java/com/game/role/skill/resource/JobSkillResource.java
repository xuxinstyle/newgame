package com.game.role.skill.resource;

import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 17:31
 */
@LoadResource
public class JobSkillResource {
    /**
     * 玩家职业
     */
    private int id;
    /**
     * 技能组合
     */
    private int[] skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getSkills() {
        return skills;
    }

    public void setSkills(int[] skills) {
        this.skills = skills;
    }
}

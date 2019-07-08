package com.game.role.skill.resource;

import com.game.user.condition.model.LearnSkillCondition;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;


/**
 * @Author：xuxin
 * @Date: 2019/7/8 12:29
 */
@LoadResource
public class SkillResource {
    /**
     * 唯一标识id
     */
    private int id;
    /**
     * 技能名称
     */
    private String skillName;
    /**
     * 技能最大等级
     */
    private int maxLevel;
    /**
     * 技能类型（被动技能或主动技能）
     */
    private int skillType;
    /**
     * 职业限制
     */
    private int jobType;
    /**
     * 使用距离
     */
    private long useDis;
    /**
     * 学习条件
     */
    private String learnCondition;

    @Analyze("analyzeLearnCondition")
    private LearnSkillCondition learnSkillCondition;


    public void analyzeLearnCondition(){
        this.learnSkillCondition = LearnSkillCondition.valueOf(learnCondition);
    }

    public LearnSkillCondition getLearnSkillCondition() {
        return learnSkillCondition;
    }

    public void setLearnSkillCondition(LearnSkillCondition learnSkillCondition) {
        this.learnSkillCondition = learnSkillCondition;
    }

    public long getUseDis() {
        return useDis;
    }

    public void setUseDis(long useDis) {
        this.useDis = useDis;
    }

    public String getLearnCondition() {
        return learnCondition;
    }

    public void setLearnCondition(String learnCondition) {
        this.learnCondition = learnCondition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getSkillType() {
        return skillType;
    }

    public void setSkillType(int skillType) {
        this.skillType = skillType;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }
}

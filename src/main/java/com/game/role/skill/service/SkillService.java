package com.game.role.skill.service;

import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 17:37
 */
public interface SkillService {
    /**
     * 获取SkillResource资源
     * @param id
     * @return
     */
    SkillResource getSkillResource(int id);

    /**
     * 获取SkillLevelResource资源
     * @param id
     * @return
     */
    SkillLevelResource getSkillLevelResource(String id);

    /**
     * 获取JobSkillResource资源
     * @param jobType
     * @return
     */
    JobSkillResource getJobSkillResource(int jobType);

    /**
     * 保存技能数据
     * @param skillEnt
     */
    void saveSkill(SkillEnt skillEnt);

    /**
     * 学习技能
     * @param skillId 技能槽id
     */
    void learnSkill(int skillId,long playerId);

    /**
     * 获取SkillEnt
     * @param playerId
     * @return
     */
    SkillEnt getSkillEnt(long playerId);

    /**
     * 升级技能
     * @param playerId
     * @param skillId
     */
    void upgradeSkill(long playerId, int skillId);

    /**
     * 查看技能栏
     * @param playerId
     */
    void showSkillBar(long playerId);
}

package com.game.role.skill.service;

import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.scence.fight.model.CreatureUnit;
import com.socket.core.session.TSession;

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
    void showSkillInfo(long playerId);

    /**
     * 查看快捷技能栏
     * @param playerId
     */
    void showSkillBar(long playerId);

    /**
     * 设置 技能栏
     * @param session
     * @param setStr
     */
    void setSkillBar(TSession session,long playerId, String setStr);


    /**
     * 对怪物使用技能
     * @param session
     * @param mapId
     * @param sceneId
     * @param skillTargetId
     * @param useId
     * @param skillBarId
     */
    void useSkillToMonster(TSession session, int mapId, int sceneId, long skillTargetId, long useId, int skillBarId);

    /**
     * 使用技能
     * @param accountId
     * @param mapId
     * @param sceneId
     * @param skillBarId
     * @param targetId
     * @param useId
     */
    void doUseSkill(String accountId,int mapId, int sceneId, int skillBarId, long targetId, long useId);

    /**
     * 做生物死后复活定时处理
     * @param mapId
     * @param creatureUnit
     */
    void doCreatureDeadAfter(int mapId, CreatureUnit creatureUnit);

    /**
     * 做生物复活的操作
     * @param mapId
     * @param creatureUnit
     */
    void doCreatureRevive(int mapId, CreatureUnit creatureUnit);
}

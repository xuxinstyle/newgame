package com.game.role.skill.service;

import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.scence.base.model.AbstractScene;
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
     *
     * @param session
     * @param mapId
     * @param sceneId
     * @param targetId
     * @param useId
     * @param useType
     * @param skillBarId
     * @param targetType
     */
    void useSkill(TSession session, int mapId, int sceneId, long targetId, long useId, ObjectType useType, int skillBarId, ObjectType targetType);

    /**
     *
     * @param accountId
     * @param mapId
     * @param useId
     * @param targetId
     * @param targetType
     * @param skillBarId
     */
    void doUseSkill(String accountId, int mapId, long useId, ObjectType useType, long targetId, ObjectType targetType, int skillBarId);


}

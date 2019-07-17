package com.game.role.skill.packet;

import com.game.base.gameobject.constant.ObjectType;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 20:23
 */
public class CM_UseSkill {
    /**
     * 地图id
     */
    private int mapId;
    /**
     * 场景id
     */
    private int sceneId;
    /**
     * 技能目标id
     */
    private long skillTargetId;
    /**
     * 技能使用者id
     */
    private long useId;
    /**
     * 使用者类型
     */
    private ObjectType useType;
    /**
     * 技能栏id
     */
    private int skillBarId;
    /**
     * 目标类型
     */
    private ObjectType objectType;

    public ObjectType getUseType() {
        return useType;
    }

    public void setUseType(ObjectType useType) {
        this.useType = useType;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public int getSkillBarId() {
        return skillBarId;
    }

    public void setSkillBarId(int skillBarId) {
        this.skillBarId = skillBarId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public long getSkillTargetId() {
        return skillTargetId;
    }

    public void setSkillTargetId(long skillTargetId) {
        this.skillTargetId = skillTargetId;
    }

    public long getUseId() {
        return useId;
    }

    public void setUseId(long useId) {
        this.useId = useId;
    }
}

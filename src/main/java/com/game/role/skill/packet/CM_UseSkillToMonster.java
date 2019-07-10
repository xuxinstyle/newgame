package com.game.role.skill.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 20:23
 */
public class CM_UseSkillToMonster {
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
     * 技能栏id
     */
    private int skillBarId;

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

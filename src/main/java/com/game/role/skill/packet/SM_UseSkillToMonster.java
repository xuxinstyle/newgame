package com.game.role.skill.packet;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 11:48
 */
public class SM_UseSkillToMonster {
    /**
     * 角色id
     */
    private long unitId;
    /**
     * 角色名
     */
    private String unitName;
    /**
     * 技能id
     */
    private int skillId;
    /**
     * 目标id,目标所受伤害
     */
    private Map<Long ,Long> realHurtMap;

    public Map<Long, Long> getRealHurtMap() {
        return realHurtMap;
    }

    public void setRealHurtMap(Map<Long, Long> realHurtMap) {
        this.realHurtMap = realHurtMap;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

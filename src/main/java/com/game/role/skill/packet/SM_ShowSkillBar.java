package com.game.role.skill.packet;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/9 11:04
 */
public class SM_ShowSkillBar {
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 技能栏map
     */
    private Map<Integer, Integer> skillBarMap;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Map<Integer, Integer> getSkillBarMap() {
        return skillBarMap;
    }

    public void setSkillBarMap(Map<Integer, Integer> skillBarMap) {
        this.skillBarMap = skillBarMap;
    }
}

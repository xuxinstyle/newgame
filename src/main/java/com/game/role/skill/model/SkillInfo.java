package com.game.role.skill.model;

import com.game.SpringContext;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.player.model.Player;
import com.game.util.PlayerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 玩家技能信息
 *
 * @Author：xuxin
 * @Date: 2019/7/8 14:41
 */
public class SkillInfo {
    /**
     * 技能集合《技能唯一id， 技能信息》
     */
    private Map<Integer, SkillSlot> skillSlotMap;
    /**
     * 技能栏 <技能栏id, 技能唯一id>
     */
    private Map<Integer, Integer> skillBarMap;
    /**
     * 默认技能
     */
    private int defaultSkill;

    public static SkillInfo valueOf(Player player) {
        JobSkillResource jobSkillResource = SpringContext.getSkillService().getJobSkillResource(player.getPlayerJob());
        int[] skills = jobSkillResource.getSkills();
        SkillInfo skillInfo = new SkillInfo();
        skillInfo.setDefaultSkill(skills[0]);
        Map<Integer, SkillSlot> skillSlotMap = new HashMap<>(skills.length);
        Map<Integer, Integer> skillBarMap = new HashMap<>(PlayerUtil.SKILL_SLOT_NUM);
        for (int i = 0; i < skills.length; i++) {
            SkillSlot skillSlot = new SkillSlot();
            skillSlot.setCanUse(false);
            skillSlot.setId(skills[i]);
            skillSlot.setLevel(1);
            skillSlotMap.put(skills[i], skillSlot);
        }
        for (int i = 1; i <= 5; i++) {
            skillBarMap.put(i, -1);
        }
        skillBarMap.put(1,0);
        skillInfo.setSkillSlotMap(skillSlotMap);
        skillInfo.setSkillBarMap(skillBarMap);
        SkillSlot skillSlot = skillSlotMap.get(skillInfo.getDefaultSkill());
        skillSlot.setCanUse(true);
        return skillInfo;
    }

    public Map<Integer, SkillSlot> getSkillSlotMap() {
        return skillSlotMap;
    }

    public void setSkillSlotMap(Map<Integer, SkillSlot> skillSlotMap) {
        this.skillSlotMap = skillSlotMap;
    }

    public Map<Integer, Integer> getSkillBarMap() {
        return skillBarMap;
    }

    public void setSkillBarMap(Map<Integer, Integer> skillBarMap) {
        this.skillBarMap = skillBarMap;
    }

    public int getDefaultSkill() {
        return defaultSkill;
    }

    public void setDefaultSkill(int defaultSkill) {
        this.defaultSkill = defaultSkill;
    }
}

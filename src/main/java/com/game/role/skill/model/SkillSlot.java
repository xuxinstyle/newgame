package com.game.role.skill.model;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 15:12
 */
public class SkillSlot {
    /**
     * 技能id
     */
    private int id;
    /**
     * 技能等级
     */
    private int level;
    /**
     * 是否允许使用
     */
    private boolean isCanUse = true;

    public static SkillSlot valueOf(int skillId, int level, boolean isCanUse) {
        SkillSlot skillSlot = new SkillSlot();
        skillSlot.setId(skillId);
        skillSlot.setLevel(level);
        skillSlot.setCanUse(isCanUse);
        return skillSlot;
    }

    /**
     * 深克隆
     *
     * @return
     */
    public SkillSlot deepCopy() {
        SkillSlot skillSlot = new SkillSlot();
        skillSlot.setCanUse(isCanUse);
        skillSlot.setLevel(level);
        skillSlot.setId(id);
        return skillSlot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isCanUse() {
        return isCanUse;
    }

    public void setCanUse(boolean canUse) {
        isCanUse = canUse;
    }
}

package com.game.role.skill.constant;

import com.game.scence.fight.model.CreatureUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 12:23
 */
public enum SkillType {
    /**
     * 普通单体伤害类型的技能 单体技能
     */
    SINGLE_SKILL(1){
        @Override
        public List<CreatureUnit> getAllUnit(CreatureUnit creatureUnit, long range) {
            List<CreatureUnit> creatUnits = new ArrayList<>();
            if(range==0){
                creatUnits.add(creatureUnit);
            }
            return creatUnits;
        }
    },
    /**
     * 多目标技能
     */
    GROUP_SKILL(2){

    },
    /**
     * AOE技能
     */
    AOE_SKILL(3),
    /**
     * 属性buff技能
     */
    ATTRIBUTE_BUFF_SKILL(4),
    ;
    int id;

    public List<CreatureUnit> getAllUnit(CreatureUnit creatureUnit,long range){

        return null;
    }
    SkillType(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

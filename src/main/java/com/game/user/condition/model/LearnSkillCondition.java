package com.game.user.condition.model;

import com.game.role.player.model.Player;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 21:12
 */
public class LearnSkillCondition extends AbstractCondition{
    /**
     * 玩家等级
     */
    private int level;

    public static LearnSkillCondition valueOf(String conditionStr){
        if(conditionStr==null||"".equals(conditionStr)){
            return null;
        }
        LearnSkillCondition condition = new LearnSkillCondition();
        condition.setLevel(Integer.parseInt(conditionStr));
        return condition;
    }
    @Override
    public boolean checkCondition(Player player, Map<String, Object> param) {
        if(player.getLevel()>=level){
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
